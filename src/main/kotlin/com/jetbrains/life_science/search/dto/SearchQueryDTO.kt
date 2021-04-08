package com.jetbrains.life_science.search.dto

data class SearchQueryDTO(

    val query: String,

    val tags: List<String> = emptyList(),

    val exclusionTypes: List<String> = emptyList()

)
