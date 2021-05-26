package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo
import com.jetbrains.life_science.article.section.service.SectionCreationInfo

class SectionCreationDTOInfoAdapter(
    val dto: SectionInnerDTO
) : SectionCreationInfo {

    override val name: String = dto.name

    override val description: String = dto.description

    override val visible: Boolean = dto.visible

    override val contentCreationInfo: ContentCreationInfo? = dto.content?.let { object: ContentCreationInfo {
        override val text: String = dto.content.text
        override val references: List<String> = dto.content.references
        override val tags: List<String> = dto.content.tags
    }  }
}
