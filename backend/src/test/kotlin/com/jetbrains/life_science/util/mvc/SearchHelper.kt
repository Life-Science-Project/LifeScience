package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.search.dto.SearchQueryDTO
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class SearchHelper(
    private val mvc: MockMvc
): BaseTestHelper() {


    fun getSearchResults(searchQueryDTO: SearchQueryDTO): List<Map<String, String>> {
        val json = getSearchRawResults(searchQueryDTO)
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
        return jsonMapper.readValue(json, List::class.java) as List<Map<String, String>>
    }

    private fun getSearchRawResults(searchQueryDTO: SearchQueryDTO): ResultActionsDsl {
        return mvc.post("/api/search"){
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(searchQueryDTO)
            accept = MediaType.APPLICATION_JSON
        }
    }



}
