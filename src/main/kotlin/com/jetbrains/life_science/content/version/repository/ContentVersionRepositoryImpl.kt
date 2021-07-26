package com.jetbrains.life_science.content.version.repository

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.util.elastic.CustomIndexElasticsearchRepository
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Repository

@Repository
class ContentVersionRepositoryImpl(
    val elasticsearchOperations: ElasticsearchOperations
) : ContentVersionRepository, CustomIndexElasticsearchRepository("content_version") {

    override fun findBySectionId(sectionId: Long): Content? {
        val criteria = Criteria("sectionId").`is`(sectionId)
        val query = CriteriaQuery(criteria)
        return elasticsearchOperations.searchOne(query, Content::class.java, indexCoordinates)?.content
    }

    override fun deleteBySectionId(sectionId: Long) {
        val criteria = Criteria("sectionId").`is`(sectionId)
        val query = CriteriaQuery(criteria)
        elasticsearchOperations.delete(query, Content::class.java, indexCoordinates)
    }

    override fun save(content: Content): Content {
        return elasticsearchOperations.save(content, indexCoordinates)
    }

    override fun deleteById(id: String) {
        elasticsearchOperations.delete(id, indexCoordinates)
    }

    override fun findById(id: String): Content? {
        return elasticsearchOperations.get(id, Content::class.java, indexCoordinates)
    }
}
