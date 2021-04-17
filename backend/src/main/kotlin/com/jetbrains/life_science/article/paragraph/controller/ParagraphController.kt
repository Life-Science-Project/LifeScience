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

    @GetMapping("/{paragraphId}")
    fun getParagraph(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
    ): ParagraphView {
        val paragraph = paragraphService.findById(paragraphId)
        checkIdEquality(sectionId, paragraph.sectionId)
        return mapper.createView(paragraph)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createContent(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        checkIdEquality(sectionId, dto.sectionId)
        val paragraph = paragraphService.create(
            ParagraphDTOToInfoAdapter(dto)
        )
        return mapper.createView(paragraph)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{paragraphId}")
    fun updateContent(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        val paragraph = paragraphService.findById(paragraphId)
        checkIdEquality(sectionId, paragraph.sectionId)
        val updatedParagraph = paragraphService.update(
            paragraphId,
            ParagraphDTOToInfoAdapter(dto)
        )
        return mapper.createView(updatedParagraph)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{paragraphId}")
    fun deleteContent(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
        principal: Principal
    ) {
        val paragraph = paragraphService.findById(paragraphId)
        checkIdEquality(sectionId, paragraph.sectionId)
        paragraphService.delete(paragraphId)
    }

    private fun checkIdEquality(
        sectionId: Long,
        entityId: Long
    ) {
        if (sectionId != entityId) {
            throw ParagraphNotFoundException("Paragraph's section id and request section id doesn't match")
        }
    }
}
