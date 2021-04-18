package com.jetbrains.life_science.article.paragraph.dto

import com.jetbrains.life_science.article.paragraph.service.ParagraphInfo

class ParagraphDTOToInfoAdapter(
    val dto: ParagraphDTO,
    override val id: String? = null
) : ParagraphInfo {

    override val sectionId: Long
        get() = dto.sectionId

    override val text: String
        get() = dto.text

    override val references: MutableList<String>
        get() = dto.references

    override val tags: MutableList<String>
        get() = dto.tags
}
