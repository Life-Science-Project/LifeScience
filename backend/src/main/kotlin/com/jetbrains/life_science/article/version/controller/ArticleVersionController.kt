package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTOToInfoAdapter
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import com.jetbrains.life_science.exception.ArticleNotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions")
class ArticleVersionController(
    val service: ArticleVersionService,
    val mapper: ArticleVersionViewMapper,
    val userService: UserService
) {

    @GetMapping
    fun getVersions(@PathVariable articleId: Long): List<ArticleVersionView> {
        return service.getByArticleId(articleId).map { mapper.createView(it) }
    }

    @GetMapping("/{versionId}")
    fun getVersion(@PathVariable articleId: Long, @PathVariable versionId: Long): ArticleVersionView {
        val version = service.getById(versionId)
        checkIdEquality(articleId, version.mainArticle.id)
        return mapper.createView(
            version
        )
    }

    @PostMapping
    fun createVersion(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        checkIdEquality(articleId, dto.articleId)
        val user = userService.getByName(principal.name)
        val createdVersion = service.createBlank(
            ArticleVersionDTOToInfoAdapter(dto, user)
        )
        return mapper.createView(createdVersion)
    }

    @PutMapping("/{versionId}")
    fun updateVersion(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        val articleVersion = service.getById(versionId)
        checkIdEquality(articleId, articleVersion.mainArticle.id)
        val user = userService.getByName(principal.name)
        val updatedVersion = service.updateById(
            ArticleVersionDTOToInfoAdapter(dto, user, versionId),
        )
        return mapper.createView(updatedVersion)
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

    private fun checkIdEquality(
        articleId: Long,
        entityId: Long
    ) {
        if (articleId != entityId) {
            throw ArticleNotFoundException("ArticleVersion's article id and request article id doesn't match")
        }
    }
}
