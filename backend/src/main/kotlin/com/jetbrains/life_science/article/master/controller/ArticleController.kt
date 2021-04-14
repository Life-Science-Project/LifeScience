package com.jetbrains.life_science.article.master.controller

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.dto.ArticleDTOToInfoAdapter
import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    val articleService: ArticleService,
    val mapper: ArticleViewMapper
) {

    @GetMapping
    fun getArticles(): List<ArticleView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long): List<ArticleView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @PostMapping
    fun createArticle(@Validated @RequestBody dto: ArticleDTO, principal: Principal): ArticleView {
        articleService.create(ArticleDTOToInfoAdapter(dto))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
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
