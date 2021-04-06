package com.jetbrains.life_science.search.service

import com.jetbrains.life_science.search.units.SearchResult

interface SearchService {

    fun search(data: SearchInfo): List<SearchResult>
}
