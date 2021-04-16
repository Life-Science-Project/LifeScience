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
    ): List<ContentView> {
        return contentService.findAllBySectionId(sectionId).map { mapper.createView(it) }
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
    ): ContentView {
        val content =  contentService.findById(contentId)
        if (sectionId != content.sectionId) {
            throw ContentNotFoundException("Content's section id and request section id doesn't match")
        }
        return mapper.createView(content)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        if (sectionId != dto.sectionId) {
            throw ContentNotFoundException("Content's dto section id and request section id doesn't match")
        }
        return mapper.createView(
            contentService.create(
                ContentDTOToInfoAdapter(dto)
            )
        )
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
        if (sectionId != content.sectionId) {
            throw ContentNotFoundException("Content's dto section id and request section id doesn't match")
        }
        return mapper.createView(
            contentService.update(
                contentId,
                ContentDTOToInfoAdapter(dto))
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{contentId}/text")
    fun updateContentText(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @RequestBody text: String,
        principal: Principal
    ): ContentView {
        TODO("Do we need patch endpoints?")
        return mapper.createView(
            contentService.updateText(contentId, text)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ) {
        val content = contentService.findById(contentId)
        if (sectionId != content.sectionId) {
            throw ContentNotFoundException("Content's section id and request section id doesn't match")
        }
        contentService.delete(contentId)
    }
}
