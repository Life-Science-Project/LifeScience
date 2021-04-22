package com.jetbrains.life_science.article.content.version.controller

import com.jetbrains.life_science.article.content.master.dto.ContentDTO
import com.jetbrains.life_science.article.content.master.dto.ContentDtoToInfoAdapter
import com.jetbrains.life_science.article.content.master.view.ContentView
import com.jetbrains.life_science.article.content.master.view.ContentViewMapper
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.AccessDeniedException
import com.jetbrains.life_science.exception.ContentNotFoundException
import com.jetbrains.life_science.user.details.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/sections/{sectionId}/contents/versions")
class ContentVersionController(
    val contentVersionService: ContentVersionService,
    val viewMapper: ContentViewMapper,
    val sectionService: SectionService,
    val userService: UserService
) {

    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        checkIdEquality(sectionId, dto.sectionId)
        checkAccess(principal, sectionId)
        val content = contentVersionService.create(ContentDtoToInfoAdapter(dto))
        return viewMapper.createView(content)
    }

    @PutMapping("/{contentId}")
    fun updateContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @Validated @RequestBody dto: ContentDTO,
        principal: Principal
    ): ContentView {
        checkAccess(principal, sectionId)
        val content = contentVersionService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        checkIdEquality(sectionId, dto.sectionId)
        val updatedContent = contentVersionService.update(
            ContentDtoToInfoAdapter(dto, contentId)
        )
        return viewMapper.createView(updatedContent)
    }

    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ) {
        checkAccess(principal, sectionId)
        val content = contentVersionService.findById(contentId)
        checkIdEquality(sectionId, content.sectionId)
        contentVersionService.delete(contentId)
    }

    private fun checkAccess(principal: Principal, sectionId: Long) {
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
            throw ContentNotFoundException("Content's section id and request section id doesn't match")
        }
    }

}
