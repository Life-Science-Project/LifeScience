package com.jetbrains.life_science.section.entity

interface SectionInfo {
    fun getID(): Long

    fun getName(): String

    fun getParentID(): Long?
}
