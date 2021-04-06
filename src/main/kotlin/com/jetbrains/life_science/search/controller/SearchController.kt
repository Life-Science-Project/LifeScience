package com.jetbrains.life_science.search.controller

import com.jetbrains.life_science.search.dto.SearchDTO
import com.jetbrains.life_science.search.dto.SearchDTOToInfoAdapter
import com.jetbrains.life_science.search.service.SearchService
import com.jetbrains.life_science.search.units.SearchResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class SearchController(
    val service: SearchService
) {

    @PostMapping()
    fun search(@RequestBody queryDTO: SearchDTO): List<SearchResult> {
        return service.search(SearchDTOToInfoAdapter(queryDTO))
    }


}
