package com.jetbrains.life_science.article.controller

import com.jetbrains.life_science.article.dto.ArticleCreationDTO
import com.jetbrains.life_science.article.dto.ArticleCreationDTOToInfoAdapter
import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.article.entity.Article
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/article")
class ArticleController(
    val articleService: ArticleService
) {


    @PostMapping
    fun createArticle(@RequestBody dto: ArticleCreationDTO) {
        articleService.create(ArticleCreationDTOToInfoAdapter(dto))
    }


    @GetMapping
    fun getAll(): List<Article> {
        return articleService.getAll()
    }

}
