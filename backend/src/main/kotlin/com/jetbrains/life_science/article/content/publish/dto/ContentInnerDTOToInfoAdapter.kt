package com.jetbrains.life_science.article.content.publish.dto

import com.jetbrains.life_science.article.content.publish.service.ContentInfo

class ContentInnerDTOToInfoAdapter(
    override val sectionId: Long,
    val dto: ContentInnerDTO,
    override val id: String? = null
) : ContentInfo {
    override val text: String = dto.text

    override val references: List<String> = dto.references

    override val tags: List<String> = dto.tags
}
