package com.jetbrains.life_science.search.result.protocol

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class ProtocolSearchService : UnitSearchService {

    override fun process(id: String, response: Map<String, Any>): ProtocolSearchResult {
        val names = response.getOrThrow("names") { "Name not found" } as List<*>
        val approachId = response.getOrThrow("approachId") { "ApproachId not found" } as Int
        return ProtocolSearchResult(id.toLong(), approachId.toLong(), names[0].toString())
    }

    override val key = SearchUnitType.PROTOCOL
}
