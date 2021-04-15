package com.jetbrains.life_science.article.paragraph.repository

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ParagraphRepository : ElasticsearchRepository<Paragraph, String>, ParagraphRepositoryCustom {

    fun findAllBySectionId(sectionId: Long): List<Paragraph>

    fun deleteAllBySectionId(sectionId: Long)
}
