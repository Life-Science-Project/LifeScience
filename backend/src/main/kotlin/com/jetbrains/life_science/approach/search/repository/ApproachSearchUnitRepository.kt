package com.jetbrains.life_science.approach.search.repository

import com.jetbrains.life_science.approach.search.ApproachSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ApproachSearchUnitRepository : ElasticsearchRepository<ApproachSearchUnit, Long>
