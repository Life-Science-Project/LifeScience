package com.jetbrains.life_science.article.content.publish.controller

import com.jetbrains.life_science.article.content.publish.dto.ContentDTO
import com.jetbrains.life_science.article.content.publish.dto.ContentDTOToInfoAdapter
import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.publish.view.ContentView
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/sections/{sectionId}/contents")
class ContentController(
    val contentService: ContentService,
    val contentVersionService: ContentVersionService,
    val viewMapper: ContentViewMapper,
    val userService: UserService,
    val sectionService: SectionService
) {

    @Operation(summary = "Returns section's content or null, if it's not exists")
    @GetMapping
    fun getContents(
        @PathVariable sectionId: Long,
    ): ContentView? {
        val section = sectionService.getById(sectionId)
        val content = if (section.articleVersion.isPublished) {
            contentService.findBySectionId(sectionId)
        } else {
            contentVersionService.findBySectionId(sectionId)
        }
        return content?.let { viewMapper.createView(it) }
    }

    @Operation(summary = "Returns content", deprecated = true)
    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String
    ): ContentView {
        val content = contentService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        return viewMapper.createView(content)
    }

    @Operation(summary = "Creates new content associated with section")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        checkIdEquality(sectionId, dto.sectionId)
        checkAccessToEdit(principal, sectionId)
        val content = contentVersionService.create(ContentDTOToInfoAdapter(dto))
        return viewMapper.createView(content)
    }

    @Operation(summary = "Updates existing content")
    @PutMapping("/{contentId}")
    fun updateContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        checkAccessToEdit(principal, sectionId)
        val content = contentVersionService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        checkIdEquality(sectionId, dto.sectionId)
        val updatedContent = contentVersionService.update(
            ContentDTOToInfoAdapter(dto, contentId)
        )
        return viewMapper.createView(updatedContent)
    }

    @Operation(summary = "Deletes existing content")
    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ) {
        checkAccessToEdit(principal, sectionId)
        val content = contentVersionService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        contentVersionService.delete(contentId)
    }

    private fun checkAccessToEdit(principal: Principal, sectionId: Long) {
        val section = sectionService.getById(sectionId)
        val user = userService.getByEmail(principal.email)
        val sectionOwner = section.articleVersion.author
        if (user.id != sectionOwner.id) {
            throw AccessDeniedException("Section $sectionId not belongs to user ${principal.email}")
        }
    }

    private fun checkIdEquality(
        sectionId: Long,
        entityId: Long
    ) {
        if (sectionId != entityId) {
            throw BadRequestException("Content's section id and request section id doesn't match")
        }
    }
}
