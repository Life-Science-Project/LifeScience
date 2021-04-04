package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.dto.ArticleCreationDTOToInfoAdapter
import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo

interface ArticleService {

    fun createEmpty()

    fun getAll(): List<Article>

    fun searchBySubString(ss: String): List<Article>

    fun create(info: ArticleInfo)

}
