package com.jetbrains.life_science.search.dto

import com.jetbrains.life_science.exception.request.SearchUnitTypeNotSupportedException
import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType

class SearchQueryDTOToQueryInfoAdapter(val dto: SearchQueryDTO) : SearchQueryInfo {

    override val includeTypes: List<SearchUnitType>

    init {
        for (typeName in dto.includeTypes) {
            if (typeName !in SearchUnitType.names) {
                throw SearchUnitTypeNotSupportedException(typeName)
            }
        }
        includeTypes = dto.includeTypes.map { SearchUnitType.valueOf(it) }
    }

    override val text: String = dto.text

    override val from: Int = dto.from

    override val size: Int = dto.size
}
