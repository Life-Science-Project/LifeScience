package com.jetbrains.life_science.article.repository

import org.springframework.stereotype.Repository

interface ArticleRepositoryCustom {

    fun updateText(id: String, text: String)

}
