package com.jetbrains.life_science.controller.section.dto

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionCreationInfo

class SectionCreationDTOToInfoAdapter(
    val dto: SectionCreationDTO,
    override var prevSection: Section?,
    override val allSections: List<Section>
) : SectionCreationInfo {
    override val id: Long = 0L
    override val name: String = dto.name
    override val hidden: Boolean = dto.hidden
}
