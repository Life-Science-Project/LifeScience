package com.jetbrains.life_science.section.dto

import com.jetbrains.life_science.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    private val dto: SectionDTO
) : SectionInfo {

    override val id: Long
        get() = 0

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description

    override val articleVersionId: Long
        get() = dto.articleVersionId
}
