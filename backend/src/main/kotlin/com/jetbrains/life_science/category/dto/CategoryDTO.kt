package com.jetbrains.life_science.category.dto

import javax.validation.constraints.NotBlank

data class CategoryDTO(
    @field:NotBlank
    val name: String,

    val parentId: Long?,

    val order: Long
)
