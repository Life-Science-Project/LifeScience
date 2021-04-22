package com.jetbrains.life_science.article.content.version.repository

import com.jetbrains.life_science.article.content.index.IndexCoordinatesProvider
import com.jetbrains.life_science.article.content.master.entity.Content
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Repository

@Repository
class ContentVersionRepositoryImpl(
    indexCoordinatesProvider: IndexCoordinatesProvider,
    val elasticsearchOperations: ElasticsearchOperations
) : ContentVersionRepository {

    private val indexCoordinates = indexCoordinatesProvider.indexCoordinates

    override fun findAllBySectionId(sectionId: Long): List<Content> {
        val criteria = Criteria("sectionId").`is`(sectionId)
        val query = CriteriaQuery(criteria)
        return elasticsearchOperations.search(query, Content::class.java, indexCoordinates)
            .map { it.content }.toList()
    }

    override fun deleteAllBySectionId(sectionId: Long) {
        val criteria = Criteria("sectionId").`is`(sectionId)
        val query = CriteriaQuery(criteria)
        elasticsearchOperations.delete(query, indexCoordinates)
    }

    override fun saveVersion(content: Content): Content {
        return elasticsearchOperations.save(content, indexCoordinates)
    }

    override fun deleteById(id: String) {
        elasticsearchOperations.delete(id, indexCoordinates)
    }

    override fun getVersion(id: String): Content? {
        return elasticsearchOperations.get(id, Content::class.java, indexCoordinates)
    }
}
