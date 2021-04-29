package com.jetbrains.life_science.search.result.content

import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow

// @Service TODO: fix parameters and enable later
class ContentSearchService : UnitSearchService("Content") {

    override fun process(id: String, response: Map<String, Any>): ContentSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return ContentSearchResult(id, text)
    }
}
