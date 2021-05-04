package com.jetbrains.life_science.search.query

interface SearchQueryInfo {

    val text: String

    val includeTypes: List<SearchUnitType>

    val from: Int

    val size: Int
}
