package com.jetbrains.life_science.article.paragraph.controller

import com.jetbrains.life_science.article.paragraph.dto.ParagraphDTO
import com.jetbrains.life_science.article.paragraph.dto.ParagraphDTOToInfoAdapter
import com.jetbrains.life_science.article.paragraph.service.ParagraphService
import com.jetbrains.life_science.article.paragraph.view.ParagraphView
import com.jetbrains.life_science.article.paragraph.view.ParagraphViewMapper
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/sections/{sectionId}/paragraphs")
class ParagraphController(
    val paragraphService: ParagraphService,
    val paragraphMapper: ParagraphViewMapper
) {

    @GetMapping
    fun getParagraphs(
        @PathVariable sectionId: Long,
    ): List<ParagraphView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{paragraphId}")
    fun getParagraph(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
    ): ParagraphView {
        return paragraphMapper.createView(
            paragraphService.findById(paragraphId)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createParagraph(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        return paragraphMapper.createView(
            paragraphService.create(
                ParagraphDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateParagraph(
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: ParagraphDTO,
        principal: Principal
    ): ParagraphView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{paragraphId}/text")
    fun updateParagraphText(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
        @RequestBody text: String,
        principal: Principal
    ): ParagraphView {
        return paragraphMapper.createView(
            paragraphService.updateText(paragraphId, text)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{paragraphId}")
    fun deleteParagraph(
        @PathVariable sectionId: Long,
        @PathVariable paragraphId: String,
        principal: Principal
    ) {
        paragraphService.delete(paragraphId)
    }
}
