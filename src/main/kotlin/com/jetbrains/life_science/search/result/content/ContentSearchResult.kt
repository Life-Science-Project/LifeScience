package com.jetbrains.life_science.search.result.content

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

data class ContentSearchResult(
    val id: String,
    val text: String,
    val sectionId: Long
) : SearchResult(SearchUnitType.CONTENT.presentationName)
