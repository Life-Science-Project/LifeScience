package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionCreationInfo {
    val id: Long

    val name: String

    val hidden: Boolean

    val prevSection: Section?

    val allSections: List<Section>
}
