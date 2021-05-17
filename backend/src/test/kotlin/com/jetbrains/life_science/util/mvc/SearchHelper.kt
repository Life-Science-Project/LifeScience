package com.jetbrains.life_science.util.mvc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

class SearchHelper(
    val mvc: MockMvc
) {

    private val jsonMapper = jacksonObjectMapper()

    inline fun <reified T> countHits(searchQueryDTO: SearchQueryDTO, expected: T): Int {
        val views = getSearchResults(searchQueryDTO).map { jacksonObjectMapper().readValue(it, jacksonTypeRef<T>()) }
        return views.count { it == expected }
    }

    inline fun <reified T> getSearchResultsView(searchQueryDTO: SearchQueryDTO): List<T> {
        return getSearchResults(searchQueryDTO).map { jacksonObjectMapper().readValue(it, jacksonTypeRef<T>()) }
    }

    fun getSearchResults(searchQueryDTO: SearchQueryDTO): List<String> {
        val result = post("/api/search", searchQueryDTO, List::class.java) as List<Map<String, String>>
        return result.map { jsonMapper.writeValueAsString(it) }
    }

    private fun <T> post(url: String, dto: Any, viewToken: Class<T>): T? {
        val result = request(HttpMethod.POST, url, dto)
        return toView(result, viewToken)
    }

    private fun <T> toView(result: ResultActionsDsl, viewToken: Class<T>): T? {
        val json = result
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString
        if (json.isEmpty()) return null
        return jsonMapper.readValue(json, viewToken)
    }

    private fun request(method: HttpMethod, url: String, dto: Any): ResultActionsDsl {
        return mvc.request(method, url) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }
    }
}
