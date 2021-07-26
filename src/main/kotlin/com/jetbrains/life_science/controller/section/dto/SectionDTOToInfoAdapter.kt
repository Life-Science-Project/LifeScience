package com.jetbrains.life_science.controller.section.dto

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    dto: SectionDTO,
    override val allSections: List<Section>,
    override val prevSection: Section?
) : SectionInfo {
    override val content: String = dto.content
    override val name: String = dto.name
    override val visible: Boolean = !dto.hidden
}
