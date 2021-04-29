package com.jetbrains.life_science.search.result.section

import com.jetbrains.life_science.search.result.UnitSearchService
import com.jetbrains.life_science.util.getOrThrow

// @Service TODO: fix parameters and enable later
class SectionSearchService : UnitSearchService("Section") {

    override fun process(id: String, response: Map<String, Any>): SectionSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return SectionSearchResult(id, text)
    }
}
