package com.jetbrains.life_science.article.master.controller

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.master.dto.ArticleDTOToInfoAdapter
import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    val service: ArticleService,
    val mapper: ArticleViewMapper
) {

    @GetMapping("/{categoryId}")
    fun getByCategoryId(@PathVariable categoryId: Long): List<ArticleView> {
        return service.getByCategoryId(categoryId).map { mapper.createView(it) }
    }

    @PostMapping
    fun create(@Validated @RequestBody dto: ArticleDTO) {
        service.create(ArticleDTOToInfoAdapter(dto))
    }
}
