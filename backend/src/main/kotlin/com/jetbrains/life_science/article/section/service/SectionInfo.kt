package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.content.publish.service.ContentCreationInfo

interface SectionInfo {

    val id: Long

    val name: String

    val description: String

    val articleVersionId: Long

    val order: Int

    val visible: Boolean

    val contentInfo: ContentCreationInfo?
}
