package com.jetbrains.life_science.search.dto

import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType

class SearchQueryDTOToQueryInfoAdapter(val dto: SearchQueryDTO) : SearchQueryInfo {

    override val text: String = dto.text

    override val exclusionTypes: List<SearchUnitType> = dto.exclusionTypes.map { SearchUnitType.valueOf(it) }

    override val from: Int = dto.from

    override val size: Int = dto.size
}
