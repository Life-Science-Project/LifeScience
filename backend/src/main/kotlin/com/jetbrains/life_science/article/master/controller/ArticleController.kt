package com.jetbrains.life_science.article.master.controller

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.dto.ArticleDTOToInfoAdapter
import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    val articleService: ArticleService,
    val mapper: ArticleViewMapper
) {

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): ArticleView {
        return mapper.createView(
            articleService.getById(articleId)
        )
    }

    @PostMapping
    fun createArticle(@Validated @RequestBody dto: ArticleDTO, principal: Principal): ArticleView {
        return mapper.createView(
            articleService.create(ArticleDTOToInfoAdapter(dto))
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateArticle(@Validated @RequestBody dto: ArticleDTO, principal: Principal): ArticleView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{articleId}")
    fun deleteArticle(@PathVariable articleId: Long, principal: Principal): ResponseEntity<Void> {
        // TODO(#54): implement method
        // return ResponseEntity.ok().build()
        throw UnsupportedOperationException("Not yet implemented")
    }
}
