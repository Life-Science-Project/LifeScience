package com.jetbrains.life_science.article.version.search.repository

import com.jetbrains.life_science.article.version.search.ArticleVersionSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleVersionSearchUnitRepository : ElasticsearchRepository<ArticleVersionSearchUnit, Long>
