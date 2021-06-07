package com.jetbrains.life_science.search.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class SearchQueryDTO(

    val text: String,

    val includeTypes: List<String> = listOf("ARTICLE", "CONTENT"),

    @field:Max(100)
    @field:Positive
    val size: Int = 10,

    @field:PositiveOrZero
    val from: Int = 0

)
