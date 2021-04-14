package com.jetbrains.life_science.section.repository

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository : JpaRepository<Section, Long> {

    fun findAllByArticle(articleVersion: ArticleVersion): List<Section>
}
