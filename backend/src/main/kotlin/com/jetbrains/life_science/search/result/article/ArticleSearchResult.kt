package com.jetbrains.life_science.search.result.article

import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.result.SearchResult

class ArticleSearchResult(
    val versionId: Long,
    val name: String,
    val articleId: Long
) : SearchResult(SearchUnitType.ARTICLE.presentationName)
