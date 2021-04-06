package com.jetbrains.life_science.article.dto

import com.jetbrains.life_science.article.entity.ArticleInfo

class ArticleCreationDTOToInfoAdapter(
    val dto: ArticleCreationDTO
) : ArticleInfo {

    override val id: Long?
        get() = null

    override val containerId: Long
        get() = dto.containerId

    override val text: String
        get() = dto.text

    override val references: MutableList<String>
        get() = dto.references

    override val tags: MutableList<String>
        get() = dto.tags
}
