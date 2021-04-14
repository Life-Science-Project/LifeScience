package com.jetbrains.life_science.content.controller

import com.jetbrains.life_science.content.dto.ContentCreationDTO
import com.jetbrains.life_science.content.dto.ContentCreationDTOToInfoAdapter
import com.jetbrains.life_science.content.dto.ContentUpdateDTO
import com.jetbrains.life_science.content.service.ContentService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/contents")
class ContentController(
    val contentService: ContentService
) {

    @PostMapping
    fun createArticle(@Validated @RequestBody dto: ContentCreationDTO) {
        contentService.create(ContentCreationDTOToInfoAdapter(dto))
    }

    @PutMapping
    fun updateArticle(@Validated @RequestBody dto: ContentUpdateDTO) {
        contentService.updateText(dto.id, dto.text)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable("id") id: String) {
        contentService.delete(id)
    }
}
