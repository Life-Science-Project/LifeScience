package com.jetbrains.life_science.search.result.section

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow

class SectionSearchService : UnitSearchService {

    override fun process(id: String, response: Map<String, Any>): SectionSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        val articleVersionId = response.getOrThrow("articleVersionId") { "Article version id not found" } as Number
        return SectionSearchResult(id.toLong(), text, articleVersionId.toLong())
    }

    override val key = SearchUnitType.SECTION
}
