package com.jetbrains.life_science.search.result.category

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

data class CategorySearchResult(
    val categoryId: Long,
    val name: String
) : SearchResult(SearchUnitType.CATEGORY.presentationName)
