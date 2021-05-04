package com.jetbrains.life_science.search.result

import com.jetbrains.life_science.search.query.SearchUnitType

interface UnitSearchService {

    fun process(id: String, response: Map<String, Any>): SearchResult

    val key: SearchUnitType
}
