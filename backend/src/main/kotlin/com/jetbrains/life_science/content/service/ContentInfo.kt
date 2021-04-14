package com.jetbrains.life_science.content.service

interface ContentInfo {

    val id: Long?

    val sectionId: Long

    val text: String

    val references: MutableList<String>

    val tags: MutableList<String>
}
