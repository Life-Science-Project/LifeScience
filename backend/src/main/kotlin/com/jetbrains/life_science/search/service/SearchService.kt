package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.result.SearchResult

interface SearchService {

    fun search(data: SearchQueryInfo): List<SearchResult>
}
