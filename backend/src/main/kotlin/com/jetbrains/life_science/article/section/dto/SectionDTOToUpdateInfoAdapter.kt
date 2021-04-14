package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.service.SectionUpdateInfo

class SectionDTOToUpdateInfoAdapter(
    val dto: SectionUpdateDTO
) : SectionUpdateInfo {

    override val id: Long
        get() = dto.id

    override val name: String
        get() = dto.name

    override val description: String
        get() = dto.description
}
