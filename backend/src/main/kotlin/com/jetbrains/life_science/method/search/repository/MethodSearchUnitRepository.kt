package com.jetbrains.life_science.method.search.repository

import com.jetbrains.life_science.method.search.MethodSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface MethodSearchUnitRepository : ElasticsearchRepository<MethodSearchUnit, Long>
