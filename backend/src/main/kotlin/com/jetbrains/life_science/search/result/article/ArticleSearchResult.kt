package com.jetbrains.life_science.search.result.article

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

class ArticleSearchResult(
    id: String,
    val name: String
) : SearchResult(SearchUnitType.ARTICLE.presentationName, id)
