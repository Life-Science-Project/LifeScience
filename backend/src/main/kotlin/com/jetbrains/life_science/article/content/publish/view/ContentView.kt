package com.jetbrains.life_science.article.content.publish.view

data class ContentView(
    val id: String?,
    val text: String,
    val references: List<String>
)
