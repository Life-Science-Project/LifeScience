package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

interface SearchService {

    fun search(query: SearchQueryInfo): List<SearchResult>

    fun suggest(query: SearchQueryInfo): List<SearchResult>

    val supportedTypes: List<SearchUnitType>
}
