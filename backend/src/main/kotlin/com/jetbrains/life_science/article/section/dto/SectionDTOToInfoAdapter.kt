package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.parameter.dto.ParameterDTOToInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    private val dto: SectionDTO,
    override val id: Long = 0,
) : SectionInfo {

    override val name = dto.name

    override val description = dto.description

    override val articleVersionId = dto.articleVersionId

    override val parameters = dto.parameters.map { ParameterDTOToInfoAdapter(it) }

    override val order = dto.order

    override val visible = dto.visible
}
