package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.article.primary.service.ArticleService
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.section.dto.SectionInnerCreationToInfoAdapter
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

    override fun checkExistenceById(versionId: Long) {
        if (!repository.existsById(versionId)) {
            throw ArticleVersionNotFoundException("Article version with id = $versionId not found")
        }
    }

    @Transactional
    override fun create(info: ArticleVersionCreationInfo): ArticleVersion {
        val article = info.article
        var articleVersion = factory.create(info, article)
        articleVersion = repository.save(articleVersion)
        val createdSections = info.sectionsInfo.mapIndexed { index, sectionCreationInfo ->
            val sectionInfo = SectionInnerCreationToInfoAdapter(articleVersion.id, index, sectionCreationInfo)
            sectionService.create(sectionInfo)
        }
        articleVersion.sections.addAll(createdSections)
        return articleVersion
    }

    override fun changeState(version: ArticleVersion, state: State): ArticleVersion {
        version.state = state
        return repository.save(version)
    }

    override fun approve(version: ArticleVersion, destination: VersionDestination) {
        val state = when (destination) {
            VersionDestination.ARTICLE -> State.PUBLISHED_AS_ARTICLE
            VersionDestination.PROTOCOL -> State.PUBLISHED_AS_PROTOCOL
        }
        approve(version, state)
    }

    private fun approve(currentVersion: ArticleVersion, state: State) {
        // ARTICLE clears last article, but not PROTOCOL
        if (state == State.PUBLISHED_AS_ARTICLE) {
            val lastPublished = repository.findByMainArticleIdAndState(currentVersion.mainArticle.id, state)
            if (lastPublished != null) {
                if (lastPublished.id == currentVersion.id) return
                archive(lastPublished.id)
            }
        }

        currentVersion.state = state
        searchService.createSearchUnit(currentVersion)
        sectionService.publish(currentVersion.sections)
    }

    @Transactional
    override fun createCopy(versionId: Long, user: User): ArticleVersion {
        val publishedVersion = getPublished(versionId)
        val copy = factory.createCopy(publishedVersion)
        copy.author = user
        repository.save(copy)
        sectionService.createCopiesByArticle(publishedVersion, copy)
        return copy
    }

    @Transactional
    override fun getPublished(versionId: Long): ArticleVersion {
        return (
            repository.findByIdAndStateIn(
                versionId,
                listOf(State.PUBLISHED_AS_ARTICLE, State.PUBLISHED_AS_PROTOCOL)
            )
                ?: throw PublishedVersionNotFoundException("Published version to article: $versionId not found")
            )
    }

    override fun getPublishedByArticle(mainArticle: Article): ArticleVersion {
        return repository.findByMainArticleIdAndState(mainArticle.id, State.PUBLISHED_AS_ARTICLE)
            ?: throw ArticleVersionNotFoundException("Published as article version not found")
    }

    @Transactional
    override fun archive(versionId: Long) {
        val lastPublished = getById(versionId)
        if (lastPublished.state == State.PUBLISHED_AS_ARTICLE) {
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

    @Transactional
    override fun update(info: ArticleVersionInfo): ArticleVersion {
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
