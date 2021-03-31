package com.jetbrains.life_science.article

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/article")
class ArticleController(val articleService: ArticleService) {

    @GetMapping("/search/{text}")
    fun search(@PathVariable text: String): List<Article> {
        return articleService.searchArticle(text)
    }

    @PatchMapping("/{id}")
    fun editText(@PathVariable id: Long, @RequestBody text: String): Article {
        return articleService.editArticle(id, text)
    }
}
