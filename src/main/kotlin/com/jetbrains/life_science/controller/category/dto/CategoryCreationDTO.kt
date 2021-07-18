package com.jetbrains.life_science.controller.category.dto

import com.jetbrains.life_science.util.categoryNameRegex
import javax.validation.Valid
import javax.validation.constraints.Pattern

data class CategoryCreationDTO(
    @field:Pattern(
        regexp = categoryNameRegex,
        message = "Category name must contain only allowed characters"
    )
    val name: String,
    @field:Valid
    val aliases: List<CategoryAliasDTO> = listOf(),
    val initialParentId: Long? = null
)
