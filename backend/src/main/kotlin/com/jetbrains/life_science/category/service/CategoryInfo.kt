package com.jetbrains.life_science.category.service

interface CategoryInfo {
    fun getId(): Long

    fun getName(): String

    fun getParentID(): Long?
}
