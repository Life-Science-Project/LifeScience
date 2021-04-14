package com.jetbrains.life_science.category.entity

interface CategoryInfo {
    fun getID(): Long

    fun getName(): String

    fun getParentID(): Long?
}
