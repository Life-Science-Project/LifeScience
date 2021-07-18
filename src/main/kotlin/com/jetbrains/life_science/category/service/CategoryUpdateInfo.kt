package com.jetbrains.life_science.category.service

interface CategoryUpdateInfo {

    val id: Long

    val name: String

    val aliases: List<String>

    val parentsToAddIds: List<Long>

    val parentsToDeleteIds: List<Long>
}
