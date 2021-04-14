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
@RequestMapping("/api/versions")
class ArticleVersionController(
    val service: ArticleVersionService,
    val mapper: ArticleVersionViewMapper,
    val userService: UserService
) {
    @GetMapping("/{articleId}")
    fun getApprovedVersion(@PathVariable articleId: Long): ArticleVersionView {
        val version = service.getPublishedVersion(articleId)
        return mapper.createView(version)
    }

    @PostMapping("/create/blank")
    fun createBlank(@Validated @RequestBody dto: ArticleVersionDTO, principal: Principal) {
        val user = userService.getByName(principal.name)
        service.createBlank(ArticleVersionDTOToInfoAdapter(dto, user))
    }

    @PostMapping("/create/copy/{articleId}")
    fun createCopy(@PathVariable articleId: Long) {
        service.createCopy(articleId)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/approve/{id}")
    fun approve(@PathVariable id: Long) {
        service.approve(id)
    }
}
