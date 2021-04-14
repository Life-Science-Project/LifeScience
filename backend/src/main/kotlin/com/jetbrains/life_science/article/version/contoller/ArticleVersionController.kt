package com.jetbrains.life_science.article.version.contoller

import com.jetbrains.life_science.user.service.UserService
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTO
import com.jetbrains.life_science.article.version.dto.ArticleVersionDTOToInfoAdapter
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
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
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{versionId}")
    fun getVersion(@PathVariable articleId: Long, @PathVariable versionId: Long): ArticleVersionView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @PostMapping
    fun createVersion(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleVersionDTO,
        principal: Principal
    ): ArticleVersionView {
        val user = userService.getByName(principal.name)
        service.createBlank(ArticleVersionDTOToInfoAdapter(dto, user))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
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
    ) {
        service.approve(versionId)
    }
}
