package com.jetbrains.life_science.controller.category.dto

import javax.validation.constraints.NotEmpty

data class CategoryUpdateDTO(
    @field:NotEmpty
    val name: String,
    val parentsToAdd: List<Long>,
    val parentsToDelete: List<Long>,
)
