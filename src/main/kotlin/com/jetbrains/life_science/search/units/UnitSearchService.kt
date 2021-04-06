package com.jetbrains.life_science.search.units

abstract class UnitSearchService(
    val key: String
) {

    abstract fun process(id: String, response: Map<String, Any>): SearchResult
}
