package com.jetbrains.life_science.search.units.method

import com.jetbrains.life_science.search.units.UnitSearchService
import com.jetbrains.life_science.utils.getOrThrow
import org.springframework.stereotype.Service

@Service
class MethodSearchService : UnitSearchService("Method") {


    override fun process(id: String, response: Map<String, Any>): MethodSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return MethodSearchResult(id, text)
    }

}
