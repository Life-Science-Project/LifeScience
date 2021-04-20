package com.jetbrains.life_science.article.content.service

interface ContentInfo {

    val id: String?

    val sectionId: Long

    val text: String

    val references: MutableList<String>

    val tags: MutableList<String>
}
