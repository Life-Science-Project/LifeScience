package com.jetbrains.life_science.content.publish.service.maker

import com.jetbrains.life_science.content.publish.service.ContentInfo

fun makeContentInfo(
    id: String,
    sectionId: Long,
    text: String,
    references: List<String>,
    tags: List<String>
) = object : ContentInfo {
    override val id = id
    override val sectionId = sectionId
    override val text = text
    override val references = references
    override val tags = tags
}
