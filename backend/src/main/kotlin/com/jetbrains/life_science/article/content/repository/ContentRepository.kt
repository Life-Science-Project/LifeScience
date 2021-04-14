package com.jetbrains.life_science.article.content.repository

import com.jetbrains.life_science.article.content.entity.Content
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentRepository : ElasticsearchRepository<Content, String>, ContentRepositoryCustom {

    fun findAllBySectionId(containerId: Long): List<Content>

    fun deleteAllBySectionId(containerId: Long)
}
