package com.jetbrains.life_science.search.result.paragraph

import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow
import org.springframework.stereotype.Service

@Service
class ParagraphSearchService : UnitSearchService("Paragraph") {

    override fun process(id: String, response: Map<String, Any>): ParagraphSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return ParagraphSearchResult(id, text)
    }
}
