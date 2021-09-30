package com.jetbrains.life_science.controller.search

import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.dto.SearchQueryDTOToQueryInfoAdapter
import com.jetbrains.life_science.search.result.SearchResult
import com.jetbrains.life_science.search.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class SearchController(
    val service: SearchService
) {

    @Operation(summary = "Searches entities")
    @PostMapping
    fun search(@Validated @RequestBody queryDTO: SearchQueryDTO): List<SearchResult> {
        return service.search(
            SearchQueryDTOToQueryInfoAdapter(queryDTO, service.supportedTypes)
        )
    }

    @Operation(summary = "Searches entities by prefix")
    @PostMapping("/suggest")
    fun suggest(@Validated @RequestBody queryDTO: SearchQueryDTO): List<SearchResult> {
        return service.suggest(
            SearchQueryDTOToQueryInfoAdapter(queryDTO, service.supportedTypes)
        )
    }
}
