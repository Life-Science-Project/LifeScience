package com.jetbrains.life_science.search.dto

import com.jetbrains.life_science.exception.request.SearchUnitTypeNotSupportedException
import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType

class SearchQueryDTOToQueryInfoAdapter(val dto: SearchQueryDTO) : SearchQueryInfo {

    override val exclusionTypes: Array<String>

    init {
        for (typeName in dto.exclusionTypes) {
            if (typeName !in SearchUnitType.types) {
                throw SearchUnitTypeNotSupportedException(typeName)
            }
        }
        exclusionTypes = dto.exclusionTypes
    }

    override val text: String = dto.text

    override val from: Int = dto.from

    override val size: Int = dto.size
}
