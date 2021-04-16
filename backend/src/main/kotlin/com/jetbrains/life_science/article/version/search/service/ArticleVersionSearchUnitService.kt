package com.jetbrains.life_science.article.version.search.service

import com.jetbrains.life_science.article.version.entity.ArticleVersion

interface ArticleVersionSearchUnitService {

    fun createSearchUnit(version: ArticleVersion)

    fun deleteSearchUnitById(id: Long)

    fun update(version: ArticleVersion)
}
