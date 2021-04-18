package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    private val dto: SectionDTO,
    override val id: Long = 0
) : SectionInfo {

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description

    override val articleVersionId: Long
        get() = dto.articleVersionId
}
