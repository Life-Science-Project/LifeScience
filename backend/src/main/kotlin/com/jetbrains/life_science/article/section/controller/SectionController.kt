package com.jetbrains.life_science.article.section.controller

import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.article.section.view.SectionViewMapper
import com.jetbrains.life_science.exception.SectionNotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/sections")
class SectionController(
    val service: SectionService,
    val sectionViewMapper: SectionViewMapper
) {

    @GetMapping
    fun getSections(
        @PathVariable versionId: Long
    ): List<SectionView> {
        return service.getByVersionId(versionId).map { sectionViewMapper.createView(it) }
    }

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long
    ): SectionView {
        return sectionViewMapper.createView(service.getById(sectionId))
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createSection(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        if (versionId != dto.articleVersionId) {
            throw SectionNotFoundException("Section's dto article version id and request article version id doesn't match")
        }
        return sectionViewMapper.createView(
            service.create(
                SectionDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{sectionId}")
    fun updateSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        val version = service.getById(sectionId)
        if (versionId != version.articleVersion.id) {
            throw SectionNotFoundException("Section's article version id and request article version id doesn't match")
        }
        return sectionViewMapper.createView(
            service.update(
                sectionId,
                SectionDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{sectionId}/name")
    fun updateSectionName(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        @RequestBody name: String,
        principal: Principal
    ): SectionView {
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{sectionId}/description")
    fun updateSectionDescription(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        @RequestBody description: String,
        principal: Principal
    ): SectionView {
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{sectionId}")
    fun deleteSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal
    ) {
        val section = service.getById(sectionId)
        if (versionId != section.articleVersion.id) {
            throw SectionNotFoundException("Section's dto article version id and request article version id doesn't match")
        }
        service.deleteById(sectionId)
    }
}
