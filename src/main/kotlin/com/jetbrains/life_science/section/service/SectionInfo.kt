package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section

interface SectionInfo {
    val content: String

    val name: String

    val visible: Boolean

    val prevSection: Section?

    val allSections: List<Section>
}
