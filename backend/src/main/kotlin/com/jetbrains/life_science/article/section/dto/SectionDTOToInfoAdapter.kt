package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo
import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    dto: SectionDTO,
    override val id: Long = 0,
) : SectionInfo {

    override val name = dto.name

    override val description = dto.description

    override val articleVersionId = dto.articleVersionId

    override val order = dto.order

    override val visible = dto.visible

    override val contentInfo: ContentCreationInfo? = null
}
