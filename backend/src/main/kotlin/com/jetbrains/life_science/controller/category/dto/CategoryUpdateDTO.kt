package com.jetbrains.life_science.controller.category.dto

data class CategoryUpdateDTO(
    val name: String,
    val parentsToAdd: List<Long>,
    val parentsToDelete: List<Long>,
)
