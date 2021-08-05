package com.jetbrains.life_science.container.protocol.search.repository

import com.jetbrains.life_science.container.protocol.search.ProtocolSearchUnit
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ProtocolSearchUnitRepository : ElasticsearchRepository<ProtocolSearchUnit, Long>
