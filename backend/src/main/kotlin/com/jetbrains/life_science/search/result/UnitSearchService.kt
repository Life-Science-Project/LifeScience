package com.jetbrains.life_science.search.result

import com.jetbrains.life_science.search.query.SearchUnitType

abstract class UnitSearchService(
    val key: SearchUnitType
) {

    abstract fun process(id: String, response: Map<String, Any>): SearchResult
}
