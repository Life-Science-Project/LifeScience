package com.jetbrains.life_science.search.service.maker

import com.jetbrains.life_science.search.query.SearchQueryInfo
import com.jetbrains.life_science.search.query.SearchUnitType

fun makeSearchQueryInfo(
    text: String,
    includeTypes: List<SearchUnitType>,
    from: Int,
    size: Int,
) = object : SearchQueryInfo {
    override val text = text
    override val includeTypes = includeTypes
    override val from = from
    override val size = size
}
