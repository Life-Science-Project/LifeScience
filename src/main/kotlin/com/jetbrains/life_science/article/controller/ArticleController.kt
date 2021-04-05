package com.jetbrains.life_science.article.controller

import com.jetbrains.life_science.article.dto.ArticleCreationDTO
import com.jetbrains.life_science.article.dto.ArticleCreationDTOToInfoAdapter
import com.jetbrains.life_science.article.dto.ArticleUpdateDTO
import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.article.entity.Article
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/article")
class ArticleController(
    val articleService: ArticleService
) {

    @PostMapping
    fun createArticle(@Validated @RequestBody dto: ArticleCreationDTO) {
        articleService.create(ArticleCreationDTOToInfoAdapter(dto))
    }

    @PutMapping
    fun updateArticle(@Validated @RequestBody dto: ArticleUpdateDTO) {
        articleService.updateText(dto.id, dto.text)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable("id") id: String) {
        articleService.delete(id)
    }

    // TODO: remove, just for test
    @GetMapping("{containerId}")
    fun findAllByContainerId(@PathVariable containerId: Long): List<Article> {
        return articleService.findAllByContainerId(containerId)
    }

}
