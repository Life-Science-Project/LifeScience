package com.jetbrains.life_science.search.service

interface SearchQueryInfo {

    val query: String

    val tags: List<String>

    val exclusionTypes: List<String>
}
