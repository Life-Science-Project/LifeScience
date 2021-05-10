package com.jetbrains.life_science.article.master.service

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.master.factory.ArticleFactory
import com.jetbrains.life_science.article.master.repository.ArticleRepository
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.exception.not_empty.ArticleNotEmptyException
import com.jetbrains.life_science.exception.not_found.ArticleNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl(
    val factory: ArticleFactory,
    val categoryService: CategoryService,
    val repository: ArticleRepository
) : ArticleService {

    @Autowired
    lateinit var versionService: ArticleVersionService

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

    @Transactional
    override fun updateById(info: ArticleInfo): Article {
        val article = getById(info.id)
        val category = categoryService.getCategory(info.categoryId)
        factory.setParams(article, category)
        return article
    }

    override fun deleteById(articleId: Long) {
        if (versionService.getByArticleId(articleId).isNotEmpty()) {
            throw ArticleNotEmptyException("Article with id $articleId is not empty")
        }
        existById(articleId)
        repository.deleteById(articleId)
    }

    override fun countAll(): Long {
        return repository.count()
    }

    private fun existById(articleId: Long) {
        if (!repository.existsById(articleId)) {
            throw ArticleNotFoundException("Article not found by id: $articleId")
        }
    }
}
