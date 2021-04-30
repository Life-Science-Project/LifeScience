package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTOToInfoAdapter
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.exception.request.IllegalAccessException
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.util.email
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions")
class ArticleVersionController(
    val service: ArticleVersionService,
    val viewMapper: ArticleVersionViewMapper,
    val userCredentialsService: UserCredentialsService
) {

    @GetMapping
    fun getVersions(@PathVariable articleId: Long, principal: Principal): List<ArticleVersionView> {
        val userCredentials = userCredentialsService.getByEmail(principal.email)
        val articleVersions = if (userCredentials.permissionsGreaterThanUser) {
            service.getByArticleId(articleId)
        } else {
            service.getByArticleIdAndUser(articleId, userCredentials.user)
        }
        return viewMapper.createViews(articleVersions)
    }

    @GetMapping("/{versionId}")
    fun getVersion(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ): ArticleVersionView {
        val version = service.getById(versionId)
        checkIdEquality(articleId, version.mainArticle.id)
        val userCredentials = userCredentialsService.getByEmail(principal.email)
        checkPermission(userCredentials, version)
        return viewMapper.createView(version)
    }

    @PostMapping
    fun createVersion(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        checkIdEquality(articleId, dto.articleId)
        val user = userCredentialsService.getByEmail(principal.email).user
        val createdVersion = service.createBlank(
            ArticleVersionDTOToInfoAdapter(dto, user)
        )
        return viewMapper.createView(createdVersion)
    }

    @PutMapping("/{versionId}")
    fun updateVersion(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        val user = userCredentialsService.getByEmail(principal.email).user
        validateUpdateDate(versionId, articleId, dto, user)
        val updatedVersion = service.updateById(ArticleVersionDTOToInfoAdapter(dto, user, versionId))
        return viewMapper.createView(updatedVersion)
    }

    private fun validateUpdateDate(
        versionId: Long,
        articleId: Long,
        dto: ArticleVersionDTO,
        user: User
    ) {
        val articleVersion = service.getById(versionId)
        checkIdEquality(articleId, articleVersion.mainArticle.id)
        if (dto.articleId != articleId) {
            throw BadRequestException("Article version id from dto does not matches with id from path variable")
        }
        if (articleVersion.author.id != user.id) {
            throw IllegalAccessException("This version not belongs to current user")
        }
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{versionId}/approve")
    fun approve(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        service.approve(versionId)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{versionId}/archive")
    fun archive(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        service.archive(versionId)
    }

    private fun checkPermission(userCredentials: UserCredentials, articleVersion: ArticleVersion) {
        // If trying to get published version
        if (articleVersion.state == State.PUBLISHED) return
        // If trying to get user's version
        if (articleVersion.author.id == userCredentials.user.id) return
        // If admin or moderator wants to check version
        if (userCredentials.roles.any { it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR" }) return
        // Otherwise user do not have permission to get version
        throw IllegalAccessException("User can not get this version")
    }

    private fun checkIdEquality(
        articleId: Long,
        entityId: Long
    ) {
        if (articleId != entityId) {
            throw BadRequestException("ArticleVersion's article id and request article id doesn't match")
        }
    }
}
