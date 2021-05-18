package com.jetbrains.life_science.article.content.publish.dto

import com.jetbrains.life_science.article.content.publish.service.ContentInfo

class ContentDTOToInfoAdapter(
    val dto: ContentDTO,
    override val id: String? = null
) : ContentInfo {

    override val sectionId: Long = dto.sectionId

    override val text: String = dto.text

    override val references: List<String> = dto.references

    override val tags: List<String> = dto.tags
}
