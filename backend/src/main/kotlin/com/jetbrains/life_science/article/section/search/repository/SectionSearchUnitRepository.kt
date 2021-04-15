package com.jetbrains.life_science.article.section.search.repository

import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface SectionSearchUnitRepository : ElasticsearchRepository<SectionSearchUnit, Long>
