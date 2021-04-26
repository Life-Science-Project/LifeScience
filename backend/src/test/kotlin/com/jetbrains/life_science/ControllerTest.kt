package com.jetbrains.life_science

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
abstract class ControllerTest<DTO, View>(
    private val name: String,
    private val viewToken: Class<View>
) {

    abstract val apiUrl: String

    @Autowired
    lateinit var mockMvc: MockMvc

    private val jsonMapper = jacksonObjectMapper()

    protected fun postToController(dto: DTO): View {
        val viewJson = postRequest(dto)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn().response.contentAsString
        return getViewFromJson(viewJson)
    }

    protected fun postRequest(dto: DTO): ResultActionsDsl {
        return mockMvc.post(this.apiUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    protected fun assertNotFound(result: ResultActionsDsl) {
        result.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            MockMvcResultMatchers.jsonPath("$.message").value("$name not found")
        }
    }

    protected fun getViewFromJson(json: String): View {
        return jsonMapper.readValue(json, viewToken)
    }
}
