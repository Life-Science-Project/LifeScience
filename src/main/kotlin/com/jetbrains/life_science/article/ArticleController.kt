package com.jetbrains.life_science.article

import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.article.view.ArticleView
import com.jetbrains.life_science.article.view.ArticleViewMapper
import com.jetbrains.life_science.method.view.MethodView
import com.jetbrains.life_science.method.view.MethodViewMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/article")
class ArticleController(
    val articleService: ArticleService,
    val articleViewMapper: ArticleViewMapper,
    val methodViewMapper: MethodViewMapper
) {

    @GetMapping("/search/{text}")
    fun search(@PathVariable text: String): List<MethodView> {
        return articleService.searchArticle(text)
            .map { article ->
                methodViewMapper.createView(article.method)
            }
    }

    @PatchMapping("/{id}")
    fun editText(@PathVariable id: Long, @RequestBody text: String): ArticleView {
        return articleViewMapper.createView(articleService.editArticle(id, text))
    }
}
