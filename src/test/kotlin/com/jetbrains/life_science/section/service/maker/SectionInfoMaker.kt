package com.jetbrains.life_science.section.service.maker

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionCreationInfo
import com.jetbrains.life_science.section.service.SectionInfo

fun makeSectionInfo(
    name: String,
    prevSection: Section?,
    visible: Boolean,
    content: String,
    allSections: List<Section>
): SectionInfo = object : SectionInfo {
    override val content = content
    override val name = name
    override val visible = visible
    override val prevSection = prevSection
    override val allSections = allSections
}

fun makeSectionCreationInfo(
    id: Long,
    name: String,
    prevSection: Section?,
    visible: Boolean,
    allSections: List<Section>
): SectionCreationInfo = object : SectionCreationInfo {
    override val id = id
    override val name = name
    override val hidden = visible
    override val prevSection = prevSection
    override val allSections = allSections
}
