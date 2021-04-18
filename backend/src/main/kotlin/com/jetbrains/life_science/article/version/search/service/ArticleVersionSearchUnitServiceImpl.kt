package com.jetbrains.life_science.article.version.search.service

import com.jetbrains.life_science.exception.search_unit.ArticleSearchUnitNotFoundException
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.search.factory.ArticleSearchUnitFactory
import com.jetbrains.life_science.article.version.search.repository.ArticleVersionSearchUnitRepository
import org.springframework.stereotype.Service

@Service
class ArticleVersionSearchUnitServiceImpl(
    val repository: ArticleVersionSearchUnitRepository,
    val factory: ArticleSearchUnitFactory
) : ArticleVersionSearchUnitService {
    override fun createSearchUnit(version: ArticleVersion) {
        val searchUnit = factory.create(version)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(version: ArticleVersion) {
        checkExistsById(version.id)
        val searchUnit = factory.create(version)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ArticleSearchUnitNotFoundException("Article version search unit not found with id: $id")
        }
    }
}
