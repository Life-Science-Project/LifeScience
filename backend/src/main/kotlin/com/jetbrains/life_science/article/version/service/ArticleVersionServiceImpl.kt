package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.master.service.ArticleService
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.factory.ArticleVersionFactory
import com.jetbrains.life_science.article.version.repository.ArticleVersionRepository
import com.jetbrains.life_science.article.version.search.service.ArticleVersionSearchUnitService
import com.jetbrains.life_science.exception.not_found.ArticleVersionNotFoundException
import com.jetbrains.life_science.exception.not_found.PublishedVersionNotFoundException
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleVersionServiceImpl(
    val repository: ArticleVersionRepository,
    val factory: ArticleVersionFactory,
    val searchService: ArticleVersionSearchUnitService
) : ArticleVersionService {

    @Autowired
    lateinit var articleService: ArticleService

    @Autowired
    lateinit var sectionService: SectionService

    @Transactional
    override fun createBlank(info: ArticleVersionCreationInfo): ArticleVersion {
        val article = articleService.create(info.articleInfo)
        var articleVersion = factory.create(info, article)
        articleVersion = repository.save(articleVersion)
        return articleVersion
    }

    @Transactional
    override fun createCopy(articleId: Long, user: User): ArticleVersion {
        val publishedVersion = getPublishedVersion(articleId)
        val copy = factory.createCopy(publishedVersion)
        copy.author = user
        repository.save(copy)
        sectionService.createCopiesByArticle(publishedVersion, copy)
        return copy
    }

    @Transactional
    override fun getPublishedVersion(articleId: Long): ArticleVersion {
        return (
            repository.findByMainArticleIdAndState(articleId, State.PUBLISHED)
                ?: throw PublishedVersionNotFoundException("Published version to article: $articleId not found")
            )
    }

    @Transactional
    override fun approve(versionId: Long) {
        val currentVersion = getById(versionId)
        val lastPublished = repository.findByMainArticleIdAndState(currentVersion.mainArticle.id, State.PUBLISHED)
        if (lastPublished != null) {
            if (lastPublished.id == currentVersion.id) return
            archive(lastPublished.id)
        }
        currentVersion.state = State.PUBLISHED
        searchService.createSearchUnit(currentVersion)
        sectionService.publish(currentVersion.sections)
    }

    @Transactional
    override fun archive(versionId: Long) {
        val lastPublished = getById(versionId)
        if (lastPublished.state == State.PUBLISHED) {
            searchService.deleteSearchUnitById(lastPublished.id)
            sectionService.deleteSearchUnits(lastPublished.sections)
            sectionService.archive(lastPublished.sections)
        }
        lastPublished.state = State.ARCHIVED
    }

    override fun getById(id: Long): ArticleVersion {
        checkExistsById(id)
        return repository.findById(id).get()
    }

    override fun getByArticleId(articleId: Long): List<ArticleVersion> {
        return repository.findAllByMainArticleId(articleId)
    }

    override fun getByArticleIdAndUser(articleId: Long, user: User): List<ArticleVersion> {
        return repository.findAllByMainArticleIdAndAuthor(articleId, user)
    }

    @Transactional
    override fun updateById(info: ArticleVersionInfo): ArticleVersion {
        val version = getById(info.id)
        val article = articleService.getById(info.articleId)
        factory.setParams(version, info, article)
        return version
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ArticleVersionNotFoundException("Article version with id: $id not found")
        }
    }
}
