package com.jetbrains.life_science.article.paragraph.service

interface ParagraphInfo {

    val id: Long?

    val sectionId: Long

    val text: String

    val references: MutableList<String>

    val tags: MutableList<String>
}
