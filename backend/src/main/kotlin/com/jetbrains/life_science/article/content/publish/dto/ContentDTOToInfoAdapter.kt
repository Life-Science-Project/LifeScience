package com.jetbrains.life_science.article.content.publish.dto

import com.jetbrains.life_science.article.content.publish.service.ContentInfo

class ContentDTOToInfoAdapter(
    val dto: ContentDTO,
    override val id: String? = null
) : ContentInfo {

    override val sectionId: Long
        get() = dto.sectionId

    override val text: String
        get() = dto.text

    override val references: List<String>
        get() = dto.references

    override val tags: List<String>
        get() = dto.tags
}
