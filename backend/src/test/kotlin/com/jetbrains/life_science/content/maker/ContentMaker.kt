package com.jetbrains.life_science.content.maker

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.service.ContentCreationInfo
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

fun makeContentInfo(content: Content) = object : ContentInfo {
    override val id = content.id
    override val sectionId = content.sectionId
    override val text = content.text
    override val references = content.references
    override val tags = content.tags
}

fun makeContentCreationInfo(
    text: String,
    references: List<String>,
    tags: List<String>
) = object : ContentCreationInfo {
    override val text = text
    override val references = references
    override val tags = tags
}

fun makeContentCreationInfo(content: Content) = object : ContentCreationInfo {
    override val text = content.text
    override val references = content.references
    override val tags = content.tags
}
