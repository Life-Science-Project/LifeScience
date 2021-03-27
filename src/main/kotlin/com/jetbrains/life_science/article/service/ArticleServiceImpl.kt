package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.exceptions.ArticleNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl @Autowired constructor(
    val articleRepository: ArticleRepository
) : ArticleService {
    override fun addArticle(articleInfo: ArticleInfo) : Article {
        return articleRepository.save(Article(articleInfo.getId()))
    }

    override fun deleteArticle(id: Long) {
        existByID(id)
        articleRepository.deleteById(id)
    }

    override fun getArticle(id: Long): Article {
        existByID(id)
        return articleRepository.findById(id).orElse(Article(0))
    }

    private fun existByID(id: Long) {
        if (!articleRepository.existsById(id)) {
            throw ArticleNotFoundException("Check if the id is correct: $id")
        }
    }
}