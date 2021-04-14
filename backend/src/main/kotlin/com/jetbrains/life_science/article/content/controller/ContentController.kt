package com.jetbrains.life_science.article.content.controller

import com.jetbrains.life_science.article.content.dto.ContentDTO
import com.jetbrains.life_science.article.content.dto.ContentDTOToInfoAdapter
import com.jetbrains.life_science.article.content.service.ContentService
import com.jetbrains.life_science.article.content.view.ContentView
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions/{versionId}/contents")
class ContentController(
    val contentService: ContentService
) {

    @GetMapping
    fun getContents(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
    ): List<ContentView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable contentId: Long,
    ): ContentView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        contentService.create(ContentDTOToInfoAdapter(dto))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateContent(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{contentId}/text")
    fun updateContentText(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable contentId: String,
        @RequestBody text: String,
        principal: Principal
    ): ContentView {
        contentService.updateText(contentId, text)
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ): ResponseEntity<Void> {
        contentService.delete(contentId)
        return ResponseEntity.ok().build()
    }
}
