package com.jetbrains.life_science.auth

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.dto.NewUserDTO
import com.jetbrains.life_science.user.master.view.UserView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@Transactional
internal class AuthControllerTest :
    ControllerTest<NewUserDTO, AuthResponse>(AuthResponse::class.java) {

    init {
        apiUrl = "/api/auth"
    }

    val adminDto = NewUserDTO(
        firstName = "Admin",
        lastName = "Admin-Admin",
        email = "admin",
        password = "admin"
    )

    val newUserDto = NewUserDTO(
        firstName = "new user fn",
        lastName = "new user ln",
        email = "new@user.com",
        password = "NewUser1"
    )

    /**
     * Should create new user, and give access to their account
     */
    @Test
    internal fun `register new user`() {
        val registerResponse = register(newUserDto)
        val authUser = auth(registerResponse.tokens.jwt)
        assertEquals(registerResponse.user, authUser)
    }

    /**
     * Should get 400, because user already exists
     */
    @Test
    internal fun `register already existing user`() {
        assertBadRequest("User already exists", registerRequest(adminDto))
    }

    /**
     * Should get the user, saved in database
     */
    @Test
    internal fun `sign in user`() {
        val signinResponse = signIn(adminDto)
        val authUser = auth(signinResponse.tokens.jwt)
        assertEquals(signinResponse.user, authUser)
    }

    /**
     * Should get 404, because user doesn't exists
     */
    @Test
    internal fun `sign in non-existent user`() {
        val invalidUsername = AuthRequest("not-user", "password")
        assertNotFound("User", signInRequest(invalidUsername))
    }

    /**
     * Should get 401, because password is invalid
     */
    @Test
    internal fun `sign in invalid password`() {
        val invalidPassword = AuthRequest("admin", "not-admin")
        assertUnauthenticated(signInRequest(invalidPassword))
    }

    /**
     * Should refresh the tokens and give access to account
     */
    @Test
    internal fun `refresh auth tokens`() {
        val authTokens = signIn(adminDto).tokens
        val tokens = AuthRefreshRequest(authTokens.jwt, authTokens.refreshToken)
        val refreshResponse = refresh(tokens)

        val authUser = auth(refreshResponse.tokens.jwt)
        assertEquals(refreshResponse.user, authUser)
    }

    /**
     * Should get 401, because the jwt token is invalid
     */
    @Test
    internal fun `invalid jwt token`() {
        val authRefreshRequest = AuthRefreshRequest("babla", "asdas")
        assertUnauthenticated("Invalid JWT token", refreshRequest(authRefreshRequest))
    }

    /**
     * Should get 401, because the refresh token is invalid
     */
    @Test
    internal fun `invalid refresh token`() {
        val authTokens = signIn(adminDto).tokens
        val authRefreshRequest = AuthRefreshRequest(authTokens.jwt, "invalid refresh token")
        assertUnauthenticated("Invalid refresh token", refreshRequest(authRefreshRequest))
    }

    private fun register(dto: NewUserDTO): AuthResponse {
        val registerResponse = registerUser(dto)
        val authTokens = registerResponse.tokens
        assertNotNull(authTokens.jwt)
        assertNotNull(authTokens.refreshToken)

        val responseUser = registerResponse.user
        assertNotNull(responseUser)
        assertNotNull(responseUser.id)

        val savedUser = get(responseUser.id, UserView::class.java, "/api/users")
        val expectedUser = UserView(
            id = responseUser.id,
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
            doctorDegree = DoctorDegree.NONE,
            academicDegree = AcademicDegree.NONE,
            organisations = listOf(),
            orcid = null,
            researchId = null,
            roles = listOf("ROLE_USER")
        )
        assertEquals(expectedUser, savedUser)
        return registerResponse
    }

    private fun signIn(dto: NewUserDTO): AuthResponse {
        return signIn(AuthRequest(dto.email, dto.password))
    }

    private fun signIn(authRequest: AuthRequest): AuthResponse {
        val signinResponse = signInUser(authRequest)
        val authTokens = signinResponse.tokens
        assertNotNull(authTokens.jwt)
        assertNotNull(authTokens.refreshToken)

        val responseUser = signinResponse.user
        assertNotNull(responseUser)
        assertNotNull(responseUser.id)

        val savedUser = get(responseUser.id, UserView::class.java, "/api/users")
        assertEquals(responseUser, savedUser)
        return signinResponse
    }

    private fun refresh(oldAuthTokens: AuthRefreshRequest): AuthResponse {
        val refreshResponse = refreshUserTokens(oldAuthTokens)
        val newAuthTokens = refreshResponse.tokens

        assertNotNull(newAuthTokens.jwt)
        assertNotNull(newAuthTokens.refreshToken)
        assertNotEquals(oldAuthTokens.refreshToken, newAuthTokens.refreshToken)
        assertUnauthenticated(refreshRequest(oldAuthTokens)) // old auth tokens are invalid
        return refreshResponse
    }

    private fun registerUser(dto: NewUserDTO): AuthResponse {
        val user = assertOkAndGetJson(registerRequest(dto))
        return getViewFromJson(user, AuthResponse::class.java)
    }

    private fun signInUser(authRequest: AuthRequest): AuthResponse {
        val user = assertOkAndGetJson(signInRequest(authRequest))
        return getViewFromJson(user, AuthResponse::class.java)
    }

    private fun refreshUserTokens(tokens: AuthRefreshRequest): AuthResponse {
        val user = assertOkAndGetJson(refreshRequest(tokens))
        return getViewFromJson(user, AuthResponse::class.java)
    }

    private fun registerRequest(dto: NewUserDTO): ResultActionsDsl {
        return postRequest(dto, "$apiUrl/signup")
    }

    private fun signInRequest(dto: AuthRequest): ResultActionsDsl {
        return postRequest(dto, "$apiUrl/signin")
    }

    private fun refreshRequest(tokens: AuthRefreshRequest): ResultActionsDsl {
        return postRequest(tokens, "$apiUrl/refresh")
    }

    private fun auth(jwt: String): UserView {
        val user = mockMvc.get("/api/users/current") {
            contentType = MediaType.APPLICATION_JSON
            headers {
                setBearerAuth(jwt)
            }
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn().response.contentAsString
        return getViewFromJson(user, UserView::class.java)
    }
}
