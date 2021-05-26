package com.jetbrains.life_science.article.content.publish.service

interface ContentInfo: ContentCreationInfo {

    val id: String?

    val sectionId: Long

}
