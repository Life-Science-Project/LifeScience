package com.jetbrains.life_science.search.controller

import com.jetbrains.life_science.exception.request.SearchUnitTypeNotSupportedException
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.dto.SearchQueryDTOToQueryInfoAdapter
import com.jetbrains.life_science.search.query.SearchUnitType
import com.jetbrains.life_science.search.service.SearchService
import com.jetbrains.life_science.search.result.SearchResult
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

    @PostMapping
    fun search(@Validated @RequestBody queryDTO: SearchQueryDTO): List<SearchResult> {
        validate(queryDTO)
        return service.search(SearchQueryDTOToQueryInfoAdapter(queryDTO))
    }

    fun validate(dto: SearchQueryDTO) {
        val unexpectedType = dto.exclusionTypes.find { it !in SearchUnitType.values() }
        if (unexpectedType != null) {
            throw SearchUnitTypeNotSupportedException(unexpectedType)
        }
    }


}
