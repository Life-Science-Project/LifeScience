package com.jetbrains.life_science.search.result.category

import com.jetbrains.life_science.category.search.Path
import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

data class CategorySearchResult(
    val categoryId: Long,
    val name: String,
    val paths: List<Path>
) : SearchResult(SearchUnitType.CATEGORY.presentationName)
