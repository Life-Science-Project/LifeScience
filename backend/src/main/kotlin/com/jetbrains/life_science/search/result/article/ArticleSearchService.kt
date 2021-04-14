package com.jetbrains.life_science.search.result.article

import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class ArticleSearchService : UnitSearchService("Article") {

    override fun process(id: String, response: Map<String, Any>): ArticleSearchResult {
        val name = response.getOrThrow("name") { "Name not found" }.toString()
        return ArticleSearchResult(id, name)
    }
}
