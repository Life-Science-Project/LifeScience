package com.jetbrains.life_science.content.publish.repository

import com.jetbrains.life_science.content.publish.entity.Content
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentRepository : ElasticsearchRepository<Content, String>, ContentRepositoryCustom {

    fun findBySectionId(sectionId: Long): Content?

    fun deleteAllBySectionId(sectionId: Long)
}
