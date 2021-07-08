package com.jetbrains.life_science.controller.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jetbrains.life_science.auth.jwt.JWTServiceImpl
import com.jetbrains.life_science.auth.refresh.factory.RefreshTokenFactoryImpl
import com.jetbrains.life_science.controller.auth.view.AccessTokenView
import com.jetbrains.life_science.controller.auth.view.AuthRequestDTO
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.user.credentials.dto.NewUserDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/scripts/initial_data.sql")
internal class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var jwtServiceImpl: JWTServiceImpl

    @Autowired
    lateinit var refreshTokenFactoryImpl: RefreshTokenFactoryImpl

    val jsonMapper = jacksonObjectMapper()

    val authPath = "/api/auth"

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
        val password = "sample password"
        val login = "sample login"

        val registerTokens = register(login, password)
        pingSecured(registerTokens)

        val loginTokens = login(login, password)
        pingSecured(loginTokens)
    }

    /**
     * Success login test.
     *
     * Access token in response and refresh token in cookie expected.
     * Expected success ping secured endpoint.
     */
    @Test
    fun `login test`() {
        val tokens = login("email", "password")
        pingSecured(tokens)
    }

    /**
     * Success refresh token rest
     *
     * Expected successful login and ping secured endpoint before and after refresh.
     */
    @Test
    fun `refresh token test`() {
        val loginTokens = login("email", "password")
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
            loginRequest("wrong", "login")
        )
        assertEquals(401_005, apiExceptionView.code)
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
        assertEquals(401_003, apiExceptionView.code)
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
        assertEquals(401_001, apiExceptionView.code)
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
            val loginTokens = login("email", "password")

            // Wait for jwt to expire
            Thread.sleep(2_000)

            val pingRequest = getApiExceptionView(401, pingSecuredRequest(loginTokens))
            assertEquals(401_004, pingRequest.code)
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
            val loginTokens = login("email", "password")

            // Wait for jwt to expire
            Thread.sleep(2_000)

            val refreshRequest = getApiExceptionView(
                401,
                refreshRequest(loginTokens.refreshToken)
            )
            assertEquals(401_002, refreshRequest.code)
            assertTrue(refreshRequest.arguments.isEmpty())
        } finally {
            refreshTokenFactoryImpl.refreshExpirationSeconds = oldExpirationTime
        }
    }

    private fun pingSecured(tokens: TokenPair) {
        val pingRequest = pingSecuredRequest(tokens)
        expectOkAndReturn(pingRequest)
    }

    private fun getApiExceptionView(expectedHttpCode: Int, request: ResultActionsDsl): ApiExceptionView {
        val result = request.andExpect { status { isEqualTo(expectedHttpCode) } }.andReturn()
        return jsonMapper.readValue(result.response.contentAsString)
    }

    fun refresh(tokenPair: TokenPair): TokenPair {
        val refreshRequest = refreshRequest(tokenPair.refreshToken)
        val refreshResponse = expectOkAndReturn(refreshRequest)
        return getTokens(refreshResponse)
    }

    private fun refreshRequest(refreshToken: String) =
        mockMvc.post(makeAuthPath("/refresh")) {
            cookie(Cookie("refresh", refreshToken))
        }

    fun pingSecuredRequest(tokenPair: TokenPair) =
        mockMvc.get(pingPath) {
            cookie(Cookie("refresh", tokenPair.refreshToken))
            headers { add("Authorization", "Bearer ${tokenPair.accessToken}") }
        }

    fun register(login: String, password: String): TokenPair {
        val registerRequest = registerRequest(login, password)
        val registerResponse = expectOkAndReturn(registerRequest)
        return getTokens(registerResponse)
    }

    fun expectOkAndReturn(result: ResultActionsDsl) = expectStatusAndReturn(200, result)

    fun expectStatusAndReturn(status: Int, result: ResultActionsDsl) =
        result.andExpect { status { isEqualTo(status) } }.andReturn()

    private fun registerRequest(
        login: String,
        password: String
    ) = mockMvc.post(makeAuthPath("/register")) {
        contentType = MediaType.APPLICATION_JSON
        content = jsonMapper.writeValueAsString(NewUserDTO("", "", login, password))
        accept = MediaType.APPLICATION_JSON
    }

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    private fun login(login: String, password: String): TokenPair {
        val loginRequest = loginRequest(login, password)
        val loginResponse = expectOkAndReturn(loginRequest)
        return getTokens(loginResponse)
    }

    private fun getTokens(registerRequest: MvcResult): TokenPair {
        val cookie = registerRequest.response.cookies.find { it.name == "refresh" }!!
        val refreshToken = cookie.value
        val accessTokenView: AccessTokenView = jsonMapper.readValue(registerRequest.response.contentAsString)
        return TokenPair(accessTokenView.accessToken, refreshToken)
    }

    private fun loginRequest(
        login: String,
        password: String
    ) = mockMvc.post(makeAuthPath("/signin")) {
        contentType = MediaType.APPLICATION_JSON
        content = jsonMapper.writeValueAsString(AuthRequestDTO(login, password))
        accept = MediaType.APPLICATION_JSON
    }

    private fun makeAuthPath(pathPath: String): String {
        return authPath + pathPath
    }
}