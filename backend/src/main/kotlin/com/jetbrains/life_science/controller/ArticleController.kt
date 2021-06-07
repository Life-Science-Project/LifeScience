package com.jetbrains.life_science.controller

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.dto.ArticleDTOToInfoAdapter
import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    val articleService: ArticleService,
    val userService: UserService,
    val articleVersionViewMapper: ArticleVersionViewMapper,
    val mapper: ArticleViewMapper
) {

    @Autowired
    lateinit var articleVersionService: ArticleVersionService

    @Operation(summary = "Returns the count of all articles on the portal")
    @GetMapping("/count")
    fun getArticlesCount(): Long {
        return articleService.countAll()
    }

    @Operation(summary = "Returns all versions which are available to the user and associated with an article")
    @GetMapping("/{articleId}/versions")
    fun getVersions(@PathVariable articleId: Long, principal: Principal): List<ArticleVersionView> {
        val user = userService.getByEmail(principal.email)
        val articleVersions = if (user.isAdminOrModerator()) {
            articleService.getById(articleId).versions
        } else {
            articleService.getById(articleId).versions.filter { it.author.id == user.id }
        }
        return articleVersionViewMapper.toViews(articleVersions)
    }

    @Operation(summary = "Returns an article by it's id")
    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): ArticleView {
        val article = articleService.getById(articleId)
        return mapper.createView(article)
    }

    @PostMapping
    fun createArticle(@Validated @RequestBody dto: ArticleDTO, principal: Principal): ArticleView {
        return mapper.createView(
            articleService.create(ArticleDTOToInfoAdapter(dto))
        )
    }

    @Operation(summary = "Updates existing article")
    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{articleId}")
    fun updateArticle(
        @PathVariable articleId: Long,
        @Validated @RequestBody dto: ArticleDTO,
        principal: Principal
    ): ArticleView {
        val updatedArticle = articleService.updateById(
            ArticleDTOToInfoAdapter(dto, articleId)
        )
        return mapper.createView(updatedArticle)
    }

    @Operation(summary = "Deletes existing article")
    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{articleId}")
    fun deleteArticle(@PathVariable articleId: Long, principal: Principal) {
        articleService.deleteById(articleId)
    }
}
