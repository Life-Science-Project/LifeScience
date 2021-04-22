package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.parameter.dto.ParameterDTOToInfoAdapter
import com.jetbrains.life_science.article.section.parameter.service.ParameterInfo
import com.jetbrains.life_science.article.section.service.SectionInfo

class SectionDTOToInfoAdapter(
    private val dto: SectionDTO,
    override val id: Long = 0,
) : SectionInfo {

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description

    override val articleVersionId: Long
        get() = dto.articleVersionId

    override val parameters: List<ParameterInfo>
        get() = dto.parameters.map { ParameterDTOToInfoAdapter(it) }

    override val order: Int
        get() = dto.order
}
