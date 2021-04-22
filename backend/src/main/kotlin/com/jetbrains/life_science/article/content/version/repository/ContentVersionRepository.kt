package com.jetbrains.life_science.article.content.version.repository

import com.jetbrains.life_science.article.content.master.entity.Content

interface ContentVersionRepository {

    fun saveVersion(content: Content): Content

    fun getVersion(id: String): Content?

    fun deleteById(id: String)

    fun findAllBySectionId(sectionId: Long): List<Content>

    fun deleteAllBySectionId(sectionId: Long)
}
