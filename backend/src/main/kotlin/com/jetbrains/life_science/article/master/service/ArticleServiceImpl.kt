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
        return repository.findById(id).orElseThrow { ArticleNotFoundException("article with id $id not found") }
    }

    override fun getByCategoryId(categoryId: Long): List<Article> {
        return repository.findAllByCategoryId(categoryId)
    }
}
