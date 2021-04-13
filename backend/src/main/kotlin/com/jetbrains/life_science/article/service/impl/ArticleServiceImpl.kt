package com.jetbrains.life_science.article.service.impl

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.factory.ArticleFactory
import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.article.service.ArticleInfo
import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.service.ContainerService
import com.jetbrains.life_science.exceptions.ArticleNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl(
    val repository: ArticleRepository,
    val factory: ArticleFactory,
) : ArticleService {

    @Autowired
    lateinit var containerService: ContainerService

    override fun deleteByContainerId(containerId: Long) {
        repository.deleteAllByContainerId(containerId)
    }

    override fun findAllByContainerId(containerId: Long): List<Article> {
        return repository.findAllByContainerId(containerId)
    }

    override fun createCopiesByContainer(origin: Container, newContainer: Container) {
        val articles = repository.findAllByContainerId(origin.id)
        articles.forEach { originArticle -> copy(originArticle, newContainer) }
    }

    private fun copy(originArticle: Article, newContainer: Container) {
        val copy = factory.copy(originArticle)
        copy.containerId = newContainer.id
        repository.save(copy)
    }

    override fun create(info: ArticleInfo) {
        containerService.checkExistsById(info.containerId)
        val article = factory.create(info)
        repository.save(article)
    }

    override fun updateText(id: String, text: String) {
        checkArticleExists(id)
        repository.updateText(id, text)
    }

    override fun delete(id: String) {
        checkArticleExists(id)
        repository.deleteById(id)
    }

    private fun checkArticleExists(id: String) {
        if (!repository.existsById(id)) {
            throw ArticleNotFoundException("Article not found by id: $id")
        }
    }
}
