package com.jetbrains.life_science.search.result.category

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class CategorySearchService : UnitSearchService {

    override fun process(id: String, response: Map<String, Any>): CategorySearchResult {
        val name = response.getOrThrow("text") { "Name not found" }.toString()
        return CategorySearchResult(id.toLong(), name)
    }

    override val key = SearchUnitType.CATEGORY
}
