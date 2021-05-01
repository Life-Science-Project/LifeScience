package com.jetbrains.life_science.search.result.section

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class SectionSearchService : UnitSearchService(SearchUnitType.SECTION) {

    override fun process(id: String, response: Map<String, Any>): SectionSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return SectionSearchResult(id, text)
    }
}
