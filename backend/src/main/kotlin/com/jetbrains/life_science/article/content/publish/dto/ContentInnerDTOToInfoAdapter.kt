package com.jetbrains.life_science.article.content.publish.dto

import com.jetbrains.life_science.article.content.publish.service.ContentInfo

class ContentInnerDTOToInfoAdapter(
    override val sectionId: Long,
    val dto: ContentInnerDTO,
    override val id: String? = null
) : ContentInfo {
    override val text: String = dto.text

    override val references: MutableList<String> = dto.references

    override val tags: MutableList<String> = dto.tags
}
