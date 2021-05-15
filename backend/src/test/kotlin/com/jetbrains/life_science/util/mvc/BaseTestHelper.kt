package com.jetbrains.life_science.util.mvc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.request

abstract class BaseTestHelper(
    private val mvc: MockMvc
) {

    private val jsonMapper = jacksonObjectMapper()

    protected fun <T> getView(url: String, viewToken: Class<T>): T? {
        val request = request(HttpMethod.GET, url)
        return toView(request, viewToken)
    }

    protected fun <T> getViews(url: String, viewToken: Class<T>): List<T> {
        val json = request(HttpMethod.GET, url)
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
        val viewListType = jsonMapper.typeFactory.constructCollectionType(List::class.java, viewToken)
        return jsonMapper.readValue(json, viewListType)
    }

    protected fun <T> post(url: String, dto: Any, viewToken: Class<T>): T? {
        val result = request(HttpMethod.POST, url, dto)
        return toView(result, viewToken)
    }

    protected fun <T> patch(url: String, dto: Any, viewToken: Class<T>): T? {
        val result = request(HttpMethod.PATCH, url, dto)
        return toView(result, viewToken)
    }

    protected fun <T> toView(result: ResultActionsDsl, viewToken: Class<T>): T? {
        val json = result
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
        if (json.isEmpty()) return null
        return jsonMapper.readValue(json, viewToken)
    }

    protected fun request(method: HttpMethod, url: String, dto: Any): ResultActionsDsl {
        return mvc.request(method, url) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun request(method: HttpMethod, url: String): ResultActionsDsl {
        return mvc.request(method, url) {
            accept = MediaType.APPLICATION_JSON
        }
    }

    fun assertIsUnauthorized(result: ResultActionsDsl) {
        result.andExpect {
            status { isUnauthorized() }
        }
    }
}
