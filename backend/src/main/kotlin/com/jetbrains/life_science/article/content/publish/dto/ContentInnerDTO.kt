package com.jetbrains.life_science.article.content.publish.dto

class ContentInnerDTO(
    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
