package com.jetbrains.life_science.search.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class SearchQueryDTO(

    val text: String,

    val exclusionTypes: List<String> = emptyList(),

    @Max(100)
    @Positive
    val size: Int = 10,

    @PositiveOrZero
    val from: Int = 0

)
