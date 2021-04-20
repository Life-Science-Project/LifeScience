package com.jetbrains.life_science.article.section.parameter.dto

import com.jetbrains.life_science.article.section.parameter.service.ParameterInfo

class ParameterDTOToInfoAdapter(
    private val dto: ParameterDTO,
    override val id: Long = 0
) : ParameterInfo {

    override val name: String
        get() = dto.name

    override val defaultValue: String
        get() = dto.defaultValue
}
