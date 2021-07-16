package com.jetbrains.life_science.category.search.repository

import com.jetbrains.life_science.category.search.CategorySearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface CategorySearchUnitRepository : ElasticsearchRepository<CategorySearchUnit, Long>
