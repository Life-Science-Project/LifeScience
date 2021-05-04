package com.jetbrains.life_science.search.result.section

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

class SectionSearchResult(
    val id: Long,
    val description: String,
    val articleVersionId: Long
) : SearchResult(SearchUnitType.SECTION.presentationName)
