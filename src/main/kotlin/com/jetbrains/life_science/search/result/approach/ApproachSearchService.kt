package com.jetbrains.life_science.search.result.approach

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class ApproachSearchService : UnitSearchService {

    override fun process(id: String, response: Map<String, Any>): ApproachSearchResult {
        val names = response.getOrThrow("names") { "Name not found" } as List<*>
        return ApproachSearchResult(id.toLong(), names[0].toString())
    }

    override val key = SearchUnitType.APPROACH
}
