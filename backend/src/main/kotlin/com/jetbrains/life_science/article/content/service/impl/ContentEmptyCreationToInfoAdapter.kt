package com.jetbrains.life_science.article.content.service.impl

import com.jetbrains.life_science.article.content.service.ContentInfo

class ContentEmptyCreationToInfoAdapter(
    override val sectionId: Long
) : ContentInfo {

    override val id: Long?
        get() = null

    override val text: String
        get() = ""

    override val references: MutableList<String>
        get() = mutableListOf()

    override val tags: MutableList<String>
        get() = mutableListOf()
}
