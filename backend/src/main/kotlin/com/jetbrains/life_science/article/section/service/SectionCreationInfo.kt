package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo

interface SectionCreationInfo {

    val name: String

    val description: String

    val visible: Boolean

    val contentCreationInfo: ContentCreationInfo?
}
