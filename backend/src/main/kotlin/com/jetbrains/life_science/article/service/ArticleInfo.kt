package com.jetbrains.life_science.article.service

interface ArticleInfo {

    val id: Long?

    val containerId: Long

    val text: String

    val references: MutableList<String>

    val tags: MutableList<String>
}
