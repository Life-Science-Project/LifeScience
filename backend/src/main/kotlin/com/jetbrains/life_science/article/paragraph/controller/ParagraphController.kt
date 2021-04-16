package com.jetbrains.life_science.article.paragraph.controller

import com.jetbrains.life_science.article.paragraph.dto.ParagraphDTO
import com.jetbrains.life_science.article.paragraph.dto.ParagraphDTOToInfoAdapter
import com.jetbrains.life_science.article.paragraph.service.ParagraphService
import com.jetbrains.life_science.article.paragraph.view.ParagraphView
import com.jetbrains.life_science.article.paragraph.view.ParagraphViewMapper
import com.jetbrains.life_science.exception.ParagraphNotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/sections/{sectionId}/paragraphs")
class ParagraphController(
    val paragraphService: ParagraphService,
    val mapper: ParagraphViewMapper
) {

    @GetMapping
    fun getParagraphs(
        @PathVariable sectionId: Long,
    ): List<ParagraphView> {
        return paragraphService.findAllBySectionId(sectionId).map { mapper.createView(it) }
    }

    @GetMapping("/{contentId}")
    fun getParagraph(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
    ): ParagraphView {
        val paragraph = paragraphService.findById(contentId)
        if (sectionId != paragraph.sectionId) {
            throw ParagraphNotFoundException("Paragraph's section id and request section id doesn't match")
        }
        return mapper.createView(paragraph)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        if (sectionId != dto.sectionId) {
            throw ParagraphNotFoundException("Paragraph's dto section id and request section id doesn't match")
        }
        return mapper.createView(
            paragraphService.create(
                ParagraphDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{contentId}")
    fun updateContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        val paragraph = paragraphService.findById(contentId)
        if (sectionId != paragraph.sectionId) {
            throw ParagraphNotFoundException("Paragraph's dto section id and request section id doesn't match")
        }
        return mapper.createView(
            paragraphService.update(
                contentId,
                ParagraphDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{contentId}/text")
    fun updateContentText(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        @RequestBody text: String,
        principal: Principal
    ): ParagraphView {
        TODO("Do we need patch endpoints?")
        return mapper.createView(
            paragraphService.updateText(contentId, text)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable contentId: String,
        principal: Principal
    ) {
        val paragraph = paragraphService.findById(contentId)
        if (sectionId != paragraph.sectionId) {
            throw ParagraphNotFoundException("Paragraph's section id and request section id doesn't match")
        }
        paragraphService.delete(contentId)
    }
}
