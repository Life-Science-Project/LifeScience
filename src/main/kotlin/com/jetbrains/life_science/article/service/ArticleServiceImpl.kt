package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import com.jetbrains.life_science.article.factory.ArticleFactory
import com.jetbrains.life_science.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(
    val repository: ArticleRepository,
    val factory: ArticleFactory
) : ArticleService {

    override fun createEmpty() {
        val links = mutableListOf("http//localhost:80801")
        val tags = mutableListOf("kek", "lol")
        val article = Article(0, "1234567890 0987654321", tags, links)
        repository.save(article)
    }

    override fun create(info: ArticleInfo) {
        val article = factory.create(info)
        repository.save(article)
    }

    override fun searchBySubString(ss: String): List<Article> {
        return repository.findAll().toList()
    }

    override fun getAll(): List<Article> {
        return repository.findAll().toList()
    }

}
