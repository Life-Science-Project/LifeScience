package com.jetbrains.life_science.article.content.publish.dto

data class ContentInnerDTO(
    val text: String,

    val references: List<String>,

    val tags: List<String>
)
