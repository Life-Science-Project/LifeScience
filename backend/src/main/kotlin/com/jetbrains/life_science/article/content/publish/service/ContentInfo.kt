package com.jetbrains.life_science.article.content.publish.service

interface ContentInfo {

    val id: String?

    val sectionId: Long

    val text: String

    val references: List<String>

    val tags: List<String>
}
