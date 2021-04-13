package com.jetbrains.life_science.version.search.repository

import com.jetbrains.life_science.version.search.MethodVersionSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface MethodVersionSearchUnitRepository : ElasticsearchRepository<MethodVersionSearchUnit, Long>
