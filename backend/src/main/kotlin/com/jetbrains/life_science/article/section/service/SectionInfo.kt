package com.jetbrains.life_science.article.section.service

interface SectionInfo {

    val id: Long

    val name: String

    val description: String

    val articleVersionId: Long
}
