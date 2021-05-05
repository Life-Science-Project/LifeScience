package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.article.content.publish.dto.ContentInnerDTOToInfoAdapter
import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.section.dto.SectionInnerDTOToInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.version.dto.ArticleVersionCreationDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionCreationDTOToInfoAdapter
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTOToInfoAdapter
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import com.jetbrains.life_science.user.master.entity.UserCredentials
import com.jetbrains.life_science.user.master.service.UserCredentialsService
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.annotation.Secured
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions")
class ArticleVersionController(
    val articleVersionService: ArticleVersionService,
    val sectionService: SectionService,
    val contentService: ContentService,
    val viewMapper: ArticleVersionViewMapper,
    val userService: UserService,
    val userCredentialsService: UserCredentialsService
) {

    @GetMapping("/{versionId}")
    fun getVersion(
        @PathVariable versionId: Long,
        principal: Principal
    ): ArticleVersionView {
        val version = articleVersionService.getById(versionId)
        val userCredentials = userCredentialsService.getByEmail(principal.email)
        checkGetPermission(userCredentials, version)
        return viewMapper.createView(version)
    }

    @PostMapping
    @Transactional
    fun createNewVersion(
        @Validated @RequestBody dto: ArticleVersionCreationDTO,
        principal: Principal
    ): ArticleVersionView {
        val user = userService.getByEmail(principal.email)
        val createdVersion = articleVersionService.createBlank(
            ArticleVersionCreationDTOToInfoAdapter(dto, user)
        )
        for ((order, sectionInnerDTO) in dto.sections.withIndex()) {
            val sectionInfo = SectionInnerDTOToInfoAdapter(createdVersion.id, order, sectionInnerDTO)
            val createdSection = sectionService.create(sectionInfo)

            val contentInfo = ContentInnerDTOToInfoAdapter(createdSection.id, sectionInnerDTO.content)
            contentService.create(contentInfo)

            createdVersion.sections.add(createdSection)
        }
        return viewMapper.createView(createdVersion)
    }

    @PutMapping("/{sampleVersionId}/copy")
    fun createCopiedVersion(
        @PathVariable sampleVersionId: Long,
        principal: Principal
    ): ArticleVersionView {
        val user = userService.getByEmail(principal.email)
        val createdVersion = articleVersionService.createCopy(sampleVersionId, user)
        return viewMapper.createView(createdVersion)
    }

    @PutMapping("/{versionId}")
    fun updateVersion(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        checkUpdatePermission(versionId, principal)
        val user = userService.getByEmail(principal.email)
        val updatedVersion = articleVersionService.updateById(ArticleVersionDTOToInfoAdapter(dto, user, versionId))
        return viewMapper.createView(updatedVersion)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{versionId}/approve")
    fun approve(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        articleVersionService.approve(versionId)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{versionId}/archive")
    fun archive(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        articleVersionService.archive(versionId)
    }

    private fun checkUpdatePermission(
        versionId: Long,
        principal: Principal
    ) {
        val articleVersion = articleVersionService.getById(versionId)
        val userCredentials = userCredentialsService.getByEmail(principal.email)
        checkPermission(userCredentials, articleVersion)
    }

    private fun checkGetPermission(userCredentials: UserCredentials, articleVersion: ArticleVersion) {
        // If trying to get published version
        if (articleVersion.state == State.PUBLISHED) return
        checkPermission(userCredentials, articleVersion)
    }

    private fun checkPermission(userCredentials: UserCredentials, articleVersion: ArticleVersion) {
        // If trying to get user's version
        if (articleVersion.author.id == userCredentials.id) return
        // If admin or moderator wants to check version
        if (userCredentials.roles.any { it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR" }) return
        // Otherwise user do not have permission to get version
        throw AccessDeniedException("User has no access to that version")
    }
}
