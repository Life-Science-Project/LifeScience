package com.jetbrains.life_science.article.content.controller

import com.jetbrains.life_science.article.content.dto.ContentDTO
import com.jetbrains.life_science.article.content.dto.ContentDTOToInfoAdapter
import com.jetbrains.life_science.article.content.service.ContentService
import com.jetbrains.life_science.article.content.view.ContentView
import com.jetbrains.life_science.article.content.view.ContentViewMapper
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
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
    ): ContentView {
        return mapper.createView(
            contentService.findById(contentId)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        return mapper.createView(
            contentService.create(
                ContentDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{contentId}/text")
    fun updateContentText(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @RequestBody text: String,
        principal: Principal
    ): ContentView {
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
        contentService.delete(contentId)
    }
}
