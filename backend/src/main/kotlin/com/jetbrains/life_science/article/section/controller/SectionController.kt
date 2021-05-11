package com.jetbrains.life_science.article.section.controller

import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.article.section.view.SectionViewMapper
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.UnauthorizedException
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.user.master.service.UserCredentialsService
import com.jetbrains.life_science.util.email
import org.springframework.security.access.AccessDeniedException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/sections")
class SectionController(
    val service: SectionService,
    val userCredentialsService: UserCredentialsService,
    val articleVersionService: ArticleVersionService,
    val sectionViewMapper: SectionViewMapper
) {

    @Operation(summary = "Returns all sections associated with version")
    @GetMapping
    fun getSections(
        @PathVariable versionId: Long,
        principal: Principal?
    ): List<SectionView> {
        articleVersionService.checkExistenceById(versionId)
        checkAccess(versionId, principal)
        return service.getByVersionId(versionId).map { sectionViewMapper.createView(it) }
    }

    @Operation(summary = "Returns section")
    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal?
    ): SectionView {
        articleVersionService.checkExistenceById(versionId)
        checkAccess(versionId, principal)
        val section = service.getById(sectionId)
        return sectionViewMapper.createView(section)
    }

    @Operation(summary = "Creates new section without content")
    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createSection(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        articleVersionService.checkExistenceById(versionId)
        checkIdEquality(versionId, dto.articleVersionId)
        val section = service.create(
            SectionDTOToInfoAdapter(dto)
        )
        return sectionViewMapper.createView(section)
    }

    @Operation(summary = "Updates existing section")
    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{sectionId}")
    fun updateSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        articleVersionService.checkExistenceById(versionId)
        val version = service.getById(sectionId)
        checkIdEquality(versionId, version.articleVersion.id)
        val updatedSection = service.update(
            SectionDTOToInfoAdapter(dto, sectionId)
        )
        return sectionViewMapper.createView(updatedSection)
    }

    @Operation(summary = "Deletes existing section")
    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{sectionId}")
    fun deleteSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal
    ) {
        articleVersionService.checkExistenceById(versionId)
        val section = service.getById(sectionId)
        checkIdEquality(versionId, section.articleVersion.id)
        service.deleteById(sectionId)
    }

    private fun checkIdEquality(
        versionId: Long,
        entityId: Long
    ) {
        if (versionId != entityId) {
            throw SectionNotFoundException("Section's article version id and request article version id doesn't match")
        }
    }

    private fun checkAccess(versionId: Long, principal: Principal?) {
        val articleVersion = articleVersionService.getById(versionId)
        // If trying to get published sections
        if (articleVersion.state == State.PUBLISHED_AS_ARTICLE) return
        // If guest tries to get non-published sections
        if (principal == null) {
            throw UnauthorizedException("User is not authorized")
        }
        val userCredentials = userCredentialsService.getByEmail(principal.email)
        // If admin or moderator wants to check sections
        if (userCredentials.roles.any { it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR" }) return
        // Otherwise user do not have permission to get sections
        throw AccessDeniedException("User has no access to the sections of that articleVersion")
    }
}
