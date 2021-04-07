package com.jetbrains.life_science.article.service.impl

import com.jetbrains.life_science.article.service.ArticleInfo

class ArticleEmptyCreationToInfoAdapter(
    override val containerId: Long
) : ArticleInfo {

    override val id: Long?
        get() = null

    override val text: String
        get() = ""

    override val references: MutableList<String>
        get() = mutableListOf()

    override val tags: MutableList<String>
        get() = mutableListOf()
}
