package com.jetbrains.life_science.content.publish.dto

import com.jetbrains.life_science.content.publish.service.ContentInfo

class ContentDTOToInfoAdapter(
    val dto: ContentDTO,
) : ContentInfo {

    override var sectionId: Long = dto.sectionId

    override var text: String = dto.text

    override var references: List<String> = dto.references

    override var tags: List<String> = dto.tags
}
