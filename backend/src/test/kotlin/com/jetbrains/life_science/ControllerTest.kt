package com.jetbrains.life_science

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
abstract class ControllerTest<DTO, View>(
    private val viewToken: Class<View>
) {

    final lateinit var apiUrl: String

    @Autowired
    lateinit var mockMvc: MockMvc

    protected val jsonMapper = jacksonObjectMapper()

    protected fun <V> get(id: Any, viewToken: Class<V>, url: String = apiUrl): V {
        val entity = getRequest(id, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(entity, viewToken)
    }

    protected fun get(id: Any, url: String = apiUrl): View {
        return get(id, viewToken, url)
    }

    protected fun post(dto: DTO, url: String = apiUrl): View {
        return post(dto, url, viewToken)
    }

    protected fun <U, V> post(dto: U, url: String = apiUrl, customViewToken: Class<V>): V {
        val viewJson = postRequest(dto, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson, customViewToken)
    }

    protected fun put(id: Any, dto: DTO, url: String = apiUrl): View {
        return put(id, dto, url, viewToken)
    }

    protected fun <U, V> put(id: Any, dto: U, url: String = apiUrl, customViewToken: Class<V>): V {
        val viewJson = putRequest(id, dto, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson, customViewToken)
    }

    protected fun patch(id: Long, dto: DTO, url: String = apiUrl): View {
        return patch(id, dto, url, viewToken)
    }

    protected fun <U, V> patch(id: Long, dto: U, url: String = apiUrl, customViewToken: Class<V>): V {
        val viewJson = patchRequest(id, dto, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson, customViewToken)
    }

    protected fun delete(id: Any, url: String = apiUrl) {
        deleteRequest(id, url)
            .andExpect {
                status { isOk() }
            }
    }

    protected fun getRequest(url: String): ResultActionsDsl {
        return mockMvc.get(url)
    }

    protected fun getRequest(id: Any, url: String = apiUrl): ResultActionsDsl {
        return getRequest("$url/$id")
    }

    protected fun <U> postRequest(dto: U, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.post(url) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun <U> putRequest(id: Any, dto: U, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.put("$url/{id}", id) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun <U> patchRequest(id: Long, dto: U, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.patch("$url/{id}", id) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun deleteRequest(id: Any, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.delete("$url/{id}", id)
    }

    protected fun assertNotFound(notFoundEntityName: String, result: ResultActionsDsl) {
        result.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("$notFoundEntityName not found") }
        }
    }

    protected fun assertBadRequest(message: String, result: ResultActionsDsl) {
        result.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value(message) }
        }
    }

    protected fun assertMethodNotAllowed(result: ResultActionsDsl) {
        result.andExpect {
            status { isMethodNotAllowed() }
        }
    }

    protected fun assertOk(result: ResultActionsDsl) {
        result.andExpect {
            status { isOk() }
        }
    }

    protected fun assertOkAndGetJson(result: ResultActionsDsl): String {
        return result.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn().response.contentAsString
    }

    protected fun assertForbidden(result: ResultActionsDsl) {
        result.andExpect {
            status { isForbidden() }
        }
    }

    protected fun assertUnauthenticated(result: ResultActionsDsl) {
        result.andExpect {
            status { isUnauthorized() }
        }
    }

    protected fun assertUnauthenticated(message: String, result: ResultActionsDsl) {
        result.andExpect {
            status { isUnauthorized() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value(message) }
        }
    }

    protected fun getViewFromJson(json: String): View {
        return getViewFromJson(json, viewToken)
    }

    protected fun <U> getViewFromJson(json: String, customViewToken: Class<U>): U {
        return jsonMapper.readValue(json, customViewToken)
    }

    protected fun <U> getViewsFromJson(result: ResultActionsDsl, customViewToken: Class<U>): List<U> {
        val json = result.andReturn().response.contentAsString
        return getViewsFromJson(json, customViewToken)
    }

    protected fun getViewsFromJson(json: String): List<View> {
        return getViewsFromJson(json, viewToken)
    }

    protected fun <U> getViewsFromJson(json: String, customViewToken: Class<U>): List<U> {
        val viewListType = jsonMapper.typeFactory.constructCollectionType(List::class.java, customViewToken)
        return jsonMapper.readValue(json, viewListType)
    }
}
