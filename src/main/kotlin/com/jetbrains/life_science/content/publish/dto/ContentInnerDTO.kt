package com.jetbrains.life_science.content.publish.dto

data class ContentInnerDTO(
    val text: String,

    val references: List<String> = emptyList(),

    val tags: List<String> = emptyList()
)
