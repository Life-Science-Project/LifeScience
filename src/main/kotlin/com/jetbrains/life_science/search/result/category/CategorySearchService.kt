package com.jetbrains.life_science.search.result.category

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class CategorySearchService : UnitSearchService {

    override fun process(id: String, response: Map<String, Any>): CategorySearchResult {
        val names = response.getOrThrow("names") { "Name not found" } as List<*>
        return CategorySearchResult(id.toLong(), names[0].toString())
    }

    override val key = SearchUnitType.CATEGORY
}
