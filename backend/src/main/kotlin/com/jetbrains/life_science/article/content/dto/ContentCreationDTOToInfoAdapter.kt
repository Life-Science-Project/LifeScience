package com.jetbrains.life_science.article.content.dto

import com.jetbrains.life_science.article.content.service.ContentInfo

class ContentCreationDTOToInfoAdapter(
    val dto: ContentCreationDTO
) : ContentInfo {

    override val id: Long?
        get() = null

    override val sectionId: Long
        get() = dto.sectionId

    override val text: String
        get() = dto.text

    override val references: MutableList<String>
        get() = dto.references

    override val tags: MutableList<String>
        get() = dto.tags
}
