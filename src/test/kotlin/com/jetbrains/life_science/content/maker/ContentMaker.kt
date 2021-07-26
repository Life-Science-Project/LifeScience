package com.jetbrains.life_science.content.maker

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.service.ContentInfo

fun makeContentInfo(
    sectionId: Long,
    text: String,
    references: List<String>,
    tags: List<String>
) = object : ContentInfo {
    override var sectionId = sectionId
    override var text = text
    override var references = references
    override var tags = tags
}

fun makeContentInfo(content: Content) = object : ContentInfo {
    override var sectionId = content.sectionId
    override var text = content.text
    override var references: List<String> = content.references
    override var tags: List<String> = content.tags
}
