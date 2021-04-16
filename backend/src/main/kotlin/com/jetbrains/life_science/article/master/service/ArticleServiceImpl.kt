package com.jetbrains.life_science.article.master.service

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.master.factory.ArticleFactory
import com.jetbrains.life_science.article.master.repository.ArticleRepository
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.exception.ArticleNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl(
    val factory: ArticleFactory,
    val categoryService: CategoryService,
    val repository: ArticleRepository
) : ArticleService {

    @Transactional
    override fun create(info: ArticleInfo): Article {
        val category = categoryService.getCategory(info.categoryId)
        val article = factory.create(category)
        return repository.save(article)
    }

    override fun getById(id: Long): Article {
        existById(id)
        return repository.findById(id).get()
    }

    override fun getByCategoryId(categoryId: Long): List<Article> {
        return repository.findAllByCategoryId(categoryId)
    }

    @Transactional
    override fun updateById(articleId: Long, info: ArticleInfo): Article {
        val article = getById(articleId)
        val category = categoryService.getCategory(info.categoryId)
        factory.setParams(article, info, category)
        return article
    }

    override fun deleteById(articleId: Long) {
        existById(articleId)
        repository.deleteById(articleId)
    }

    private fun existById(articleId: Long) {
        if (!repository.existsById(articleId)) {
            throw ArticleNotFoundException("Article not found by id: $articleId")
        }
    }
}
