package com.jetbrains.life_science.category.service

interface CategoryInfo {
    val id: Long

    val name: String

    val parentId: Long?
}
