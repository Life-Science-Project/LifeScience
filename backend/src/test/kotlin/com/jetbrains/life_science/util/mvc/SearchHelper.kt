package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.search.dto.SearchQueryDTO
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class SearchHelper(
    mvc: MockMvc
) : BaseTestHelper(mvc) {

    fun getSearchResults(searchQueryDTO: SearchQueryDTO): List<Map<String, String>> {
        return post("/api/search", searchQueryDTO, List::class.java) as List<Map<String, String>>
    }
}
