package com.jetbrains.life_science.article.dto

data class ArticleCreationDTO(

    val containerId: Long,

    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
