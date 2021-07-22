package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionInfo {
    val id: Long

    val name: String

    val visible: Boolean

    val prevSection: Section?
}
