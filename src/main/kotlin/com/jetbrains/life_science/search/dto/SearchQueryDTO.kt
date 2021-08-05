package com.jetbrains.life_science.search.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Max
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class SearchQueryDTO(

    @field:Length.List(
        Length(min = 2, message = "minimum message length is 2 characters"),
        Length(max = 77, message = "maximum message length is 77 characters")
    )
    val text: String,

    val includeTypes: List<String> = listOf("CATEGORY", "APPROACH", "PROTOCOL"),

    @field:Max(100)
    @field:Positive
    val size: Int = 10,

    @field:PositiveOrZero
    val from: Int = 0

)
