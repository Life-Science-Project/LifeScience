package com.jetbrains.life_science.article.content.master.controller

import com.jetbrains.life_science.article.content.master.service.ContentService
import com.jetbrains.life_science.article.content.master.view.ContentView
import com.jetbrains.life_science.article.content.master.view.ContentViewMapper
import com.jetbrains.life_science.exception.ContentNotFoundException
import org.springframework.web.bind.annotation.*

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

    private fun checkIdEquality(
        sectionId: Long,
        entityId: Long
    ) {
        if (sectionId != entityId) {
            throw ContentNotFoundException("Content's section id and request section id doesn't match")
        }
    }
}
