package com.jetbrains.life_science.search.units.container

import com.jetbrains.life_science.search.units.UnitSearchService
import com.jetbrains.life_science.utils.getOrThrow
import org.springframework.stereotype.Service

@Service

class ContainerSearchService: UnitSearchService("Container") {


    override fun process(id: String, response: Map<String, Any>): ContainerSearchResult {
        val text = response.getOrThrow("text") { "Text not found" }.toString()
        return ContainerSearchResult(id, text)
    }

}
