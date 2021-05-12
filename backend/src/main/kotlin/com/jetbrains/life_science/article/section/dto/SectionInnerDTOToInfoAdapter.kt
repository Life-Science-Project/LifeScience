package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionInnerDTOToInfoAdapter(
    override val articleVersionId: Long,
    override val order: Int,
    dto: SectionInnerDTO,
    override val id: Long = 0,
) : SectionInfo {

    override val name = dto.name

    override val description = dto.description

    override val visible = dto.visible
}
