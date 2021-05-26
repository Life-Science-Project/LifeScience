package com.jetbrains.life_science.article.content.publish.service

interface ContentCreationInfo {
    val text: String

    val references: List<String>

    val tags: List<String>
}
