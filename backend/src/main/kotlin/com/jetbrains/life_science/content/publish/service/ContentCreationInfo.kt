package com.jetbrains.life_science.content.publish.service

interface ContentCreationInfo {
    val text: String

    val references: List<String>

    val tags: List<String>
}
