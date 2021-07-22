package com.jetbrains.life_science.category.service

interface CategoryInfo {
    val name: String

    val parentId: Long?

    val aliases: List<String>
}
