package com.jetbrains.life_science.controller.section.dto

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionInfo

class SectionCreationDTOToInfoAdapter(
    val dto: SectionCreationDTO,
    override var prevSection: Section?
    ): SectionInfo {
    override val id: Long = 0L
    override val name: String = dto.name
    override val visible: Boolean = !dto.hidden
}