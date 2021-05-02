package com.jetbrains.life_science.search.result.section

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

class SectionSearchResult(
    id: String, name: String
) : SearchResult(SearchUnitType.SECTION.presentationName, id, name)
