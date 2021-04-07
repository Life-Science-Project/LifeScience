package com.jetbrains.life_science.search.dto

import com.jetbrains.life_science.search.service.SearchQueryInfo

class SearchQueryDTOToQueryInfoAdapter(val dto: SearchQueryDTO) : SearchQueryInfo {

    override val query: String by lazy { dto.query }

    override val tags: List<String> by lazy { dto.tags }

    override val exclusionTypes: List<String> by lazy { dto.exclusionTypes }
}
