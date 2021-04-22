package com.jetbrains.life_science.article.content.master.dto

import com.jetbrains.life_science.article.content.master.service.ContentInfo

class ContentDtoToInfoAdapter(
    val dto: ContentDTO,
    override val id: String? = null
) : ContentInfo {

    override val sectionId: Long
        get() = dto.sectionId

    override val text: String
        get() = dto.text

    override val references: MutableList<String>
        get() = dto.references

    override val tags: MutableList<String>
        get() = dto.tags
}
