package com.jetbrains.life_science

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
abstract class ControllerTest<DTO, View>(
    private val viewToken: Class<View>
) {

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    final lateinit var apiUrl: String

    @Autowired
    lateinit var mockMvc: MockMvc

    private val jsonMapper = jacksonObjectMapper()

    protected fun get(id: Long, url: String = apiUrl): View {
        val entity = getRequest(id, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(entity)
    }

    protected fun post(dto: DTO, url: String = apiUrl): View {
        val viewJson = postRequest(dto, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson)
    }

    protected fun put(id: Long, dto: DTO, url: String = apiUrl): View {
        val viewJson = putRequest(id, dto, url)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson)
    }

    protected fun delete(id: Long, url: String = apiUrl) {
        deleteRequest(id, url)
            .andExpect {
                status { isOk() }
            }
    }

    protected fun getRequest(id: Long, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.get("$url/{id}", id)
    }

    protected fun postRequest(dto: DTO, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.post(url) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun putRequest(id: Long, dto: DTO, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.put("$url/{id}", id) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun deleteRequest(id: Long, url: String = apiUrl): ResultActionsDsl {
        return mockMvc.delete("$url/{id}", id)
    }

    protected fun assertNotFound(notFoundEntityName: String, result: ResultActionsDsl, message: String = "$name not found") {
        result.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value(message) }
        }
    }

    protected fun assertBadRequest(result: ResultActionsDsl, message: String = "$notFoundEntityName not found") {
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

    protected fun getViewFromJson(json: String): View {
        return jsonMapper.readValue(json, viewToken)
    }

    protected fun getViewsFromJson(json: String): List<View> {
        val viewListType = jsonMapper.typeFactory.constructCollectionType(List::class.java, viewToken)
        return jsonMapper.readValue(json, viewListType)
    }
}
