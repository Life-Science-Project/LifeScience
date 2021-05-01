package com.jetbrains.life_science.article.content.publish.controller

import com.jetbrains.life_science.article.content.publish.dto.ContentDTO
import com.jetbrains.life_science.article.content.publish.view.ContentView
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.article.content.publish.dto.ContentDTOToInfoAdapter
import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.exception.request.ContentIsNotEditableException
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import com.jetbrains.life_science.exception.request.IllegalAccessException
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
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

    @GetMapping
    fun getContents(
        @PathVariable sectionId: Long,
    ): ContentView? {
        return contentService.findBySectionId(sectionId)?.let { viewMapper.createView(it) }
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String
    ): ContentView {
        val content = contentService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        return viewMapper.createView(content)
    }

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
            throw IllegalAccessException("Section $sectionId not belongs to user ${principal.email}")
        }
        if (section.articleVersion.state != State.EDITING) {
            throw ContentIsNotEditableException("Content is not editable")
        }
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
