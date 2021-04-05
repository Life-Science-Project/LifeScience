package com.jetbrains.life_science.search.units.article

import com.jetbrains.life_science.search.units.UnitSearchService
import com.jetbrains.life_science.search.units.article.ArticleSearchResult
import com.jetbrains.life_science.utils.getOrThrow
import org.springframework.stereotype.Service

@Service
class ArticleSearchService : UnitSearchService("Article") {

    override fun process(id: String, response: Map<String, Any>): ArticleSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return ArticleSearchResult(id, text)
    }

}
