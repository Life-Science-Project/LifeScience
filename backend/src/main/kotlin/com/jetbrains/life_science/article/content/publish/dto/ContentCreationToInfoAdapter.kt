package com.jetbrains.life_science.article.content.publish.dto

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo
import com.jetbrains.life_science.article.content.publish.service.ContentInfo

class ContentCreationToInfoAdapter(
    override val sectionId: Long,
    val info: ContentCreationInfo,
) : ContentInfo {
    override val text: String = info.text

    override val references: List<String> = info.references

    override val tags: List<String> = info.tags

    override val id: String? = null
}
