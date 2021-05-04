package com.jetbrains.life_science.search.dto

import com.jetbrains.life_science.exception.request.SearchUnitTypeNotSupportedException
import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType

class SearchQueryDTOToQueryInfoAdapter(val dto: SearchQueryDTO, supportedTypes: List<SearchUnitType>) :
    SearchQueryInfo {

    override val includeTypes: List<SearchUnitType>

    init {
        val types = mutableListOf<SearchUnitType>()
        for (typeName in dto.includeTypes) {
            if (supportedTypes.none { it.name == typeName }) {
                throw SearchUnitTypeNotSupportedException(typeName)
            }
            types.add(SearchUnitType.valueOf(typeName))
        }
        includeTypes = types
    }

    override val text: String = dto.text

    override val from: Int = dto.from

    override val size: Int = dto.size
}
