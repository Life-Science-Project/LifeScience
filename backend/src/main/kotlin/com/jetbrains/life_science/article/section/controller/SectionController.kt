package com.jetbrains.life_science.article.section.controller

import com.jetbrains.life_science.article.section.dto.SectionDTO
import com.jetbrains.life_science.article.section.dto.SectionDTOToInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.section.view.SectionView
import com.jetbrains.life_science.article.section.view.SectionViewMapper
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ArticleVersionNotFoundException
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.user.master.repository.RoleRepository
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.expression.AccessException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/sections")
class SectionController(
    val service: SectionService,
    val userService: UserService,
    val articleVersionService: ArticleVersionService,
    val roleRepository: RoleRepository,
    val sectionViewMapper: SectionViewMapper
) {

    @GetMapping
    fun getSections(
        @PathVariable versionId: Long,
        principal: Principal?
    ): List<SectionView> {
        checkArticleVersionExists(versionId)
        checkAccess(versionId, principal)
        return service.getByVersionId(versionId).map { sectionViewMapper.createView(it) }
    }

    @GetMapping("/{sectionId}")
    fun getSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal?
    ): SectionView {
        checkArticleVersionExists(versionId)
        checkAccess(versionId, principal)
        val section = service.getById(sectionId)
        return sectionViewMapper.createView(section)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createSection(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        checkArticleVersionExists(versionId)
        checkIdEquality(versionId, dto.articleVersionId)
        val section = service.create(
            SectionDTOToInfoAdapter(dto)
        )
        return sectionViewMapper.createView(section)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{sectionId}")
    fun updateSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        @Validated @RequestBody dto: SectionDTO,
        principal: Principal
    ): SectionView {
        checkArticleVersionExists(versionId)
        val version = service.getById(sectionId)
        checkIdEquality(versionId, version.articleVersion.id)
        val updatedSection = service.update(
            SectionDTOToInfoAdapter(dto, sectionId)
        )
        return sectionViewMapper.createView(updatedSection)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{sectionId}")
    fun deleteSection(
        @PathVariable versionId: Long,
        @PathVariable sectionId: Long,
        principal: Principal
    ) {
        checkArticleVersionExists(versionId)
        val section = service.getById(sectionId)
        checkIdEquality(versionId, section.articleVersion.id)
        service.deleteById(sectionId)
    }

    private fun checkArticleVersionExists(
        versionId: Long
    ) {
        if (!articleVersionService.existsById(versionId)) {
            throw ArticleVersionNotFoundException("ArticleVersion with id $versionId doesn't find")
        }
    }

    private fun checkIdEquality(
        versionId: Long,
        entityId: Long
    ) {
        if (versionId != entityId) {
            throw SectionNotFoundException("Section's article version id and request article version id doesn't match")
        }
    }

    // TODO:: не должно глобально отличаться от ArticleVersion
    private fun checkAccess(versionId: Long, principal: Principal?) {
        val articleVersion = articleVersionService.getById(versionId)
        if (articleVersion.state != State.PUBLISHED) {
            if (principal == null) {
                throw AccessException("User is not authorized")
            }
            val userRole = roleRepository.findByName("ROLE_USER")
            if (userService.getByEmail(principal.email).roles.contains(userRole)) {
                throw IllegalAccessException("User can't access to the not published articleVersion")
            }
        }
    }
}
