package com.jetbrains.life_science.controller.auth

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.auth.jwt.JWTServiceImpl
import com.jetbrains.life_science.auth.refresh.factory.RefreshTokenFactoryImpl
import com.jetbrains.life_science.controller.auth.dto.NewUserDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.*
import javax.servlet.http.Cookie

@Sql("/scripts/initial_data.sql")
internal class AuthControllerTest : ApiTest() {

    @Autowired
    lateinit var jwtServiceImpl: JWTServiceImpl

    @Autowired
    lateinit var refreshTokenFactoryImpl: RefreshTokenFactoryImpl

    val pingPath = "/api/ping/secured"

    /**
     * Success register test.
     *
     * Access token in response and refresh token in cookie expected.
     * Expected success attempt to login by this credentials.
     * Expected success ping secured endpoint.
     */
    @Test
    fun `register test`() {
        val password = "sample_password123=+"
        val login = "sobaka@mail.ru"

        val registerTokens = register(login, password)
        pingSecured(registerTokens)

        val loginTokens = login(login, password)
        pingSecured(loginTokens)
    }

    /**
     * Already used email test.
     *
     * Expected 400 http code and 400_004 system code result.
     */
    @Test
    fun `already used email test`() {
        val expectedEmail = "admin@gmail.ru"
        val apiExceptionView = getApiExceptionView(
            400,
            registerRequest(expectedEmail, "pass123")
        )
        assertEquals(400_004, apiExceptionView.systemCode)
        assertEquals("admin@gmail.ru", apiExceptionView.arguments[0][0])
    }

    /**
     * Invalid email format register test.
     *
     * Expected 400 http code and 400_005 system code result.
     */
    @Test
    fun `invalid email format register test`() {
        val apiExceptionView = getApiExceptionView(
            400,
            registerRequest("wrongwrong.com", "pass123456")
        )
        assertEquals(400_005, apiExceptionView.systemCode)
        assertEquals("Email must be valid", apiExceptionView.arguments[0][0])
    }

    /**
     * Invalid password format register test.
     *
     * Expected 400 http code and 400_005 system code result.
     */
    @Test
    fun `invalid password format register test`() {
        val apiExceptionView = getApiExceptionView(
            400,
            registerRequest("abc@mail.ru", "                ")
        )
        assertEquals(400_005, apiExceptionView.systemCode)
        assertEquals("Password must contain only allowed characters", apiExceptionView.arguments[0][0])
    }

    /**
     * Success login test.
     *
     * Access token in response and refresh token in cookie expected.
     * Expected success ping secured endpoint.
     */
    @Test
    fun `login test`() {
        val tokens = login("admin@gmail.ru", "password")
        pingSecured(tokens)
    }

    /**
     * Success refresh token rest
     *
     * Expected successful login and ping secured endpoint before and after refresh.
     */
    @Test
    fun `refresh token test`() {
        val loginTokens = login("admin@gmail.ru", "password")
        pingSecured(loginTokens)

        val refreshedTokens = refresh(loginTokens)

        assertNotEquals(loginTokens, refreshedTokens)
        pingSecured(refreshedTokens)
    }

    /**
     * Invalid credentials test.
     *
     * Expected 401 http code and 401_005 system code result.
     */
    @Test
    fun `invalid credentials test`() {
        val apiExceptionView = getApiExceptionView(
            401,
            loginRequest("wrong@wrong.com", "wrond_pass999")
        )
        assertEquals(401_005, apiExceptionView.systemCode)
        assertTrue(apiExceptionView.arguments.isEmpty())
    }

    /**
     * Invalid access token test.
     *
     * Expected 401 http code and 401_003 system code result.
     */
    @Test
    fun `invalid access token test`() {
        val apiExceptionView = getApiExceptionView(
            401,
            pingSecuredRequest(TokenPair("wrong token", "error"))
        )
        assertEquals(401_003, apiExceptionView.systemCode)
        assertTrue(apiExceptionView.arguments.isEmpty())
    }

    /**
     * Invalid refresh token test.
     *
     * Expected 401 http code and 401_001 system code result.
     */
    @Test
    fun `invalid refresh token test`() {
        val apiExceptionView = getApiExceptionView(
            401,
            refreshRequest("error")
        )
        assertEquals(401_001, apiExceptionView.systemCode)
        assertTrue(apiExceptionView.arguments.isEmpty())
    }

    /**
     * Expired access token test.
     *
     * Expected 401 http code and 401_004 system code result.
     */
    @Test
    fun `expired access token test`() {
        val oldExpirationTime = jwtServiceImpl.jwtExpirationSeconds
        try {
            jwtServiceImpl.jwtExpirationSeconds = 1
            val loginTokens = login("admin@gmail.ru", "password")

            // Wait for jwt to expire
            Thread.sleep(2_000)

            val pingRequest = getApiExceptionView(401, pingSecuredRequest(loginTokens))
            assertEquals(401_004, pingRequest.systemCode)
            assertTrue(pingRequest.arguments.isEmpty())
        } finally {
            jwtServiceImpl.jwtExpirationSeconds = oldExpirationTime
        }
    }

    /**
     * Expired refresh token test.
     *
     * Expected 401 http code and 401_002 system code result.
     */
    @Test
    fun `expired refresh token test`() {
        val oldExpirationTime = refreshTokenFactoryImpl.refreshExpirationSeconds
        try {
            refreshTokenFactoryImpl.refreshExpirationSeconds = 1
            val loginTokens = login("admin@gmail.ru", "password")

            // Wait for jwt to expire
            Thread.sleep(2_000)

            val refreshRequest = getApiExceptionView(
                401,
                refreshRequest(loginTokens.refreshToken)
            )
            assertEquals(401_002, refreshRequest.systemCode)
            assertTrue(refreshRequest.arguments.isEmpty())
        } finally {
            refreshTokenFactoryImpl.refreshExpirationSeconds = oldExpirationTime
        }
    }

    private fun pingSecured(tokens: TokenPair) {
        val pingRequest = pingSecuredRequest(tokens)
        assertOkAndReturn(pingRequest)
    }

    fun refresh(tokenPair: TokenPair): TokenPair {
        val refreshRequest = refreshRequest(tokenPair.refreshToken)
        val refreshResponse = assertOkAndReturn(refreshRequest)
        return getTokens(refreshResponse)
    }

    private fun refreshRequest(refreshToken: String) =
        mockMvc.patch(makeAuthPath("/refresh")) {
            cookie(Cookie("refresh", refreshToken))
        }

    fun pingSecuredRequest(tokenPair: TokenPair) =
        mockMvc.get(pingPath) {
            cookie(Cookie("refresh", tokenPair.refreshToken))
            headers { add("Authorization", "Bearer ${tokenPair.accessToken}") }
        }

    fun register(email: String, password: String): TokenPair {
        val registerRequest = registerRequest(email, password)
        val registerResponse = assertOkAndReturn(registerRequest)
        return getTokens(registerResponse)
    }

    private fun registerRequest(
        email: String,
        password: String
    ) = mockMvc.post(makeAuthPath("/register")) {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(NewUserDTO("firstName", "lastName", email, password))
        accept = MediaType.APPLICATION_JSON
    }
}
