package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.article.entity.ArticleInfo

interface MethodInfo {
    fun getId() : Long

    fun getName() : String

    fun getSectionId() : Long

    fun getArticle() : ArticleInfo
}