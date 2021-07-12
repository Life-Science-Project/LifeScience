package com.jetbrains.life_science.category.service

interface CategoryUpdateInfo {

    val id: Long

    val name: String

    val parentsToAddIds: List<Long>

    val parentsToDeleteIds: List<Long>
}
