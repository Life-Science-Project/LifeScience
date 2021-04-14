package com.jetbrains.life_science.article.section.controller

import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.article.section.dto.SectionDTOToUpdateInfoAdapter
import com.jetbrains.life_science.article.section.dto.SectionUpdateDTO
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.article.section.view.SectionViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions/{versionId}/sections")
class SectionController(
    val service: SectionService,
    val sectionViewMapper: SectionViewMapper
) {

    @GetMapping
    fun getSections(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long
    ): List<SectionView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long
    ): SectionView {
        return sectionViewMapper.createView(service.getById(sectionId))
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createSection(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        service.create(SectionDTOToInfoAdapter(dto))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping
    fun updateSection(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: SectionUpdateDTO,
        principal: Principal
    ): SectionView {
        service.update(SectionDTOToUpdateInfoAdapter(dto))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{sectionId}")
    fun deleteSection(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal
    ): ResponseEntity<Void> {
        service.deleteById(sectionId)
        return ResponseEntity.ok().build()
    }
}
