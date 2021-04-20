package com.jetbrains.life_science.article.content.controller

import com.jetbrains.life_science.article.content.dto.ContentDTO
import com.jetbrains.life_science.article.content.dto.ContentDTOToInfoAdapter
import com.jetbrains.life_science.article.content.service.ContentService
import com.jetbrains.life_science.article.content.view.ContentView
import com.jetbrains.life_science.article.content.view.ContentViewMapper
import com.jetbrains.life_science.exception.ContentNotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/sections/{sectionId}/contents")
class ContentController(
    val contentService: ContentService,
    val mapper: ContentViewMapper
) {

    @GetMapping
    fun getContents(
        @PathVariable sectionId: Long,
    ): ContentView? {
        return contentService.findBySectionId(sectionId)?.let { mapper.createView(it) }
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String
    ): ContentView {
        val content = contentService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        return mapper.createView(content)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        checkIdEquality(sectionId, dto.sectionId)
        val content = contentService.create(ContentDTOToInfoAdapter(dto))
        return mapper.createView(content)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{contentId}")
    fun updateContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        val content = contentService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        val updatedContent = contentService.update(
            ContentDTOToInfoAdapter(dto, contentId)
        )
        return mapper.createView(updatedContent)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ) {
        val content = contentService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        contentService.delete(contentId)
    }

    private fun checkIdEquality(
        sectionId: Long,
        entityId: Long
    ) {
        if (sectionId != entityId) {
            throw ContentNotFoundException("Content's section id and request section id doesn't match")
        }
    }
}
