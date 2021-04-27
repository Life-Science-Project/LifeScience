package com.jetbrains.life_science

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
    protected val apiUrl: String,
    private val name: String,
    private val viewToken: Class<View>
) {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val jsonMapper = jacksonObjectMapper()

    protected fun get(id: Long): View {
        val entity = getRequest(id)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(entity)
    }

    protected fun post(dto: DTO): View {
        val viewJson = postRequest(dto)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson)
    }

    protected fun put(id: Long, dto: DTO): View {
        val viewJson = putRequest(id, dto)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson)
    }

    protected fun delete(id: Long) {
        deleteRequest(id)
            .andExpect {
                status { isOk() }
            }
    }

    protected fun getRequest(id: Long): ResultActionsDsl {
        return mockMvc.get("$apiUrl/{id}", id)
    }

    protected fun postRequest(dto: DTO): ResultActionsDsl {
        return mockMvc.post(apiUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun putRequest(id: Long, dto: DTO): ResultActionsDsl {
        return mockMvc.put("$apiUrl/{id}", id) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun deleteRequest(id: Long): ResultActionsDsl {
        return mockMvc.delete("$apiUrl/{id}", id)
    }

    protected fun assertNotFound(result: ResultActionsDsl) {
        result.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("$name not found") }
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
