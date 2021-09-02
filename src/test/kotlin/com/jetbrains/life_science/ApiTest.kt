package com.jetbrains.life_science

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jetbrains.life_science.controller.auth.dto.AuthRequestDTO
import com.jetbrains.life_science.controller.auth.view.AccessTokenView
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
abstract class ApiTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var entityManager: EntityManager

    val authPath = "/api/auth"

    val objectMapper = jacksonObjectMapper()

    final fun flushChanges() {
        entityManager.flush()
    }

    final inline fun <reified T> toView(mvcResult: MvcResult): T {
        val content = mvcResult.response.contentAsString
        return objectMapper.readValue(content)
    }

    fun getRequest(path: String): ResultActionsDsl {
        return mockMvc.get(path) {
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun getAuthorized(path: String, accessToken: String): ResultActionsDsl {
        return mockMvc.get(path) {
            accept = MediaType.APPLICATION_JSON
            headers { add("Authorization", "Bearer $accessToken") }
        }
    }

    final inline fun <reified T> postAuthorized(path: String, requestBody: Any, accessToken: String): T {
        return toViewSuccess(postRequestAuthorized(path, requestBody, accessToken))
    }

    final inline fun <reified T> post(path: String, requestBody: Any): T {
        return toViewSuccess(postRequest(path, requestBody))
    }

    final fun deleteAuthorized(path: String, accessToken: String): MvcResult {
        return deleteRequestAuthorized(path, accessToken).andExpect { status { isOk() } }.andReturn()
    }

    fun deleteRequestAuthorized(path: String, accessToken: String): ResultActionsDsl {
        return mockMvc.delete(path) {
            headers { add("Authorization", "Bearer $accessToken") }
        }
    }

    fun deleteRequest(path: String): ResultActionsDsl {
        return mockMvc.delete(path)
    }

    final inline fun <reified T> patchAuthorized(path: String, requestBody: Any, accessToken: String): T {
        return toViewSuccess(patchRequestAuthorized(path, requestBody, accessToken))
    }

    fun patchRequestAuthorized(path: String, requestBody: Any, accessToken: String): ResultActionsDsl {
        return mockMvc.patch(path) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestBody)
            headers { add("Authorization", "Bearer $accessToken") }
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun patchRequest(path: String, requestBody: Any): ResultActionsDsl {
        return mockMvc.patch(path) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestBody)
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun patchRequest(path: String): ResultActionsDsl {
        return mockMvc.patch(path) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun postRequest(path: String, requestBody: Any?): ResultActionsDsl {
        return mockMvc.post(path) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestBody)
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun postRequestAuthorized(path: String, requestBody: Any, accessToken: String): ResultActionsDsl {
        return mockMvc.post(path) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestBody)
            headers { add("Authorization", "Bearer $accessToken") }
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun patchAuthorized(path: String, accessToken: String) {
        patchRequestAuthorized(path, accessToken).andExpect { status { isOk() } }.andReturn()
    }

    fun patchRequestAuthorized(path: String, accessToken: String): ResultActionsDsl {
        return mockMvc.patch(path) {
            contentType = MediaType.APPLICATION_JSON
            headers { add("Authorization", "Bearer $accessToken") }
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun postAuthorized(path: String, accessToken: String) {
        postRequestAuthorized(path, accessToken).andExpect { status { isOk() } }.andReturn()
    }

    fun postRequestAuthorized(path: String, accessToken: String): ResultActionsDsl {
        return mockMvc.post(path) {
            contentType = MediaType.APPLICATION_JSON
            headers { add("Authorization", "Bearer $accessToken") }
            accept = MediaType.APPLICATION_JSON
        }
    }

    final inline fun <reified T> getView(path: String): T {
        return toViewSuccess(getRequest(path))
    }

    final inline fun <reified T> getViewAuthorized(path: String, accessToken: String): T {
        val result = getAuthorized(path, accessToken).andExpect { status { isOk() } }.andReturn()
        return toView(result)
    }

    final inline fun <reified T> toViewSuccess(result: ResultActionsDsl): T {
        val response = result.andExpect { status { isOk() } }.andReturn()
        return toView(response)
    }

    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )

    fun loginAccessToken(loginValue: String, password: String): String {
        return login(loginValue, password).accessToken
    }

    fun login(login: String, password: String): TokenPair {
        val loginRequest = loginRequest(login, password)
        val loginResponse = assertOkAndReturn(loginRequest)
        return getTokens(loginResponse)
    }

    fun getTokens(registerRequest: MvcResult): TokenPair {
        val cookie = registerRequest.response.cookies.find { it.name == "refresh" }!!
        val refreshToken = cookie.value
        val accessTokenView: AccessTokenView = objectMapper.readValue(registerRequest.response.contentAsString)
        return TokenPair(accessTokenView.accessToken, refreshToken)
    }

    fun loginRequest(
        email: String,
        password: String
    ) = mockMvc.post(makeAuthPath("/signin")) {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(AuthRequestDTO(email, password))
        accept = MediaType.APPLICATION_JSON
    }

    fun makeAuthPath(pathPath: String): String {
        return authPath + pathPath
    }

    fun assertOkAndReturn(result: ResultActionsDsl) = assertStatusAndReturn(200, result)

    fun assertStatusAndReturn(status: Int, result: ResultActionsDsl) =
        result.andExpect { status { isEqualTo(status) } }.andReturn()

    fun getApiExceptionView(expectedHttpCode: Int, request: ResultActionsDsl): ApiExceptionView {
        val result = request.andExpect { status { isEqualTo(expectedHttpCode) } }.andReturn()
        return objectMapper.readValue(result.response.contentAsString)
    }

    fun timeOf(year: Int, month: Int, day: Int): LocalDateTime {
        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }
}
