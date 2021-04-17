package com.jetbrains.life_science.article.version.controller

import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTOToInfoAdapter
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions")
class ArticleVersionController(
    val service: ArticleVersionService,
    val mapper: ArticleVersionViewMapper,
    val userCredentialsService: UserCredentialsService
) {

    @GetMapping
    fun getVersions(@PathVariable articleId: Long): List<ArticleVersionView> {
        return service.getByArticleId(articleId).map { mapper.createView(it) }
    }

    @GetMapping("/{versionId}")
    fun getVersion(@PathVariable articleId: Long, @PathVariable versionId: Long): ArticleVersionView {
        return mapper.createView(
            service.getById(versionId)
        )
    }

    @PostMapping
    fun createVersion(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        val user = userCredentialsService.getByEmail(principal.name)
        return mapper.createView(
            service.createBlank(
                ArticleVersionDTOToInfoAdapter(dto, user)
            )
        )
    }

    @PutMapping
    fun updateVersion(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/{versionId}/approve")
    fun approve(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        principal: Principal
    ): ResponseEntity<Void> {
        service.approve(versionId)
        return ResponseEntity.ok().build()
    }
}
