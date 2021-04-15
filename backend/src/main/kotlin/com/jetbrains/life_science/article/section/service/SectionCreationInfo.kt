package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.version.entity.ArticleVersion

interface SectionCreationInfo {

    val article: ArticleVersion

    val name: String

    val description: String
}
