package com.jetbrains.life_science.article.content.repository

import com.jetbrains.life_science.article.content.entity.Content

interface ContentRepositoryCustom {

    fun updateText(id: String, text: String)
}
