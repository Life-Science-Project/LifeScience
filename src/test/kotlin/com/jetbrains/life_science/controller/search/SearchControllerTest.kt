package com.jetbrains.life_science.controller.search

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.result.SearchResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SearchControllerTest : ApiTest() {

    /**
     * Test check validation exception on too small string
     */
    @Test
    fun `too small query test`() {
        val dto = SearchQueryDTO(
            text = "a",
            includeTypes = listOf("CATEGORY")
        )

        val request = postRequest("/api/search", dto)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_005, exceptionView.systemCode)
        assertEquals(listOf(listOf("minimum message length is 2 characters")), exceptionView.arguments)
    }

    /**
     * Test check validation exception on too big string
     */
    @Test
    fun `too big query test`() {
        val dto = SearchQueryDTO(
            text = "a".repeat(78),
            includeTypes = listOf("CATEGORY")
        )

        val request = postRequest("/api/search", dto)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_005, exceptionView.systemCode)
        assertEquals(listOf(listOf("maximum message length is 77 characters")), exceptionView.arguments)
    }

    /**
     * Test check that text length 77 is ok
     */
    @Test
    fun `large query test`() {
        val dto = SearchQueryDTO(
            text = "aжу時光機器ww_❄=====aжу時光機器ww_❄=====aжу時光機器ww_❄=====aжу時光機器ww_❄=====777",
            includeTypes = listOf("CATEGORY")
        )

        post<List<SearchResult>>("/api/search", dto)
    }

    /**
     * Test check that text length 2 is ok
     */
    @Test
    fun `small query test`() {
        val dto = SearchQueryDTO(
            text = "光7",
            includeTypes = listOf("CATEGORY")
        )

        post<List<SearchResult>>("/api/search", dto)
    }
}
