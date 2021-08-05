package com.jetbrains.life_science.search.result.category

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jetbrains.life_science.category.search.Path
import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class CategorySearchService : UnitSearchService {

    val jacksonMapper = jacksonObjectMapper()
    val pathsReference = object : TypeReference<List<Path>>() {}

    override fun process(id: String, response: Map<String, Any>): CategorySearchResult {
        @Suppress("UNCHECKED_CAST")
        // Because we trust in fact that elastic stores everything right
        val names = response.getOrThrow("names") { "Names not found" } as List<String>

        val paths = jacksonMapper.convertValue(
            response.getOrThrow("paths") { "Paths not found" }, pathsReference
        )
        return CategorySearchResult(id.toLong(), names[0], paths)
    }

    override val key = SearchUnitType.CATEGORY
}
