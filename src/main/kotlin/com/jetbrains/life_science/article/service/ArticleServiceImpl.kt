package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import com.jetbrains.life_science.article.factory.ArticleFactory
import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.article.repository.ArticleSearch
import com.jetbrains.life_science.exceptions.ArticleNotFoundException
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(
    val articleRepository: ArticleRepository,
    val articleFactory: ArticleFactory,
    val articleSearch: ArticleSearch
) : ArticleService {
    override fun addArticle(articleInfo: ArticleInfo): Article {
        return articleRepository.save(articleFactory.createArticle(articleInfo))
    }

    override fun deleteArticle(id: Long) {
        existByID(id)
        articleRepository.deleteById(id)
    }

    override fun getArticle(id: Long): Article {
        existByID(id)
        return articleRepository.findById(id).get()
    }

    override fun searchArticle(query: String): List<Article> {
        return articleSearch.search(query)
    }

    override fun editArticle(id: Long, text: String): Article {
        existByID(id)
        val article = articleRepository.getOne(id)
        article.text = text
        return articleRepository.save(article)
    }

    private fun existByID(id: Long) {
        if (!articleRepository.existsById(id)) {
            throw ArticleNotFoundException("Check if the id is correct: $id")
        }
    }
}
