package com.jetbrains.life_science.article.review.response.controller

import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import com.jetbrains.life_science.search.dto.SearchQueryDTO
import com.jetbrains.life_science.search.result.article.ArticleSearchResult
import com.jetbrains.life_science.util.mvc.ArticleVersionHelper
import com.jetbrains.life_science.util.mvc.ReviewHelper
import com.jetbrains.life_science.util.mvc.ReviewRequestHelper
import com.jetbrains.life_science.util.mvc.SearchHelper
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
@AutoConfigureMockMvc
internal class ReviewControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    lateinit var elasticPopulator: ElasticPopulator

    lateinit var articleVersionHelper: ArticleVersionHelper

    lateinit var searchHelper: SearchHelper

    lateinit var reviewRequestHelper: ReviewRequestHelper

    lateinit var reviewHelper: ReviewHelper

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content", "elastic/content.json")
            addPopulator("content_version", "elastic/content_version.json")
            addPopulator("article", "elastic/article.json")
            addPopulator("section", "elastic/section.json")
            prepareData()
        }

        reviewRequestHelper = ReviewRequestHelper(mockMvc)
        searchHelper = SearchHelper(mockMvc)
        articleVersionHelper = ArticleVersionHelper(mockMvc)
        reviewHelper = ReviewHelper(mockMvc)
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    @Test
    fun createReviewRequestChangesProtocol() {
        // Creating request
        val requestView = reviewRequestHelper.makeRequest(2, ReviewRequestDTO(VersionDestination.PROTOCOL.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
        // Creating response
        val reviewView = reviewHelper.makeReview(
            2,
            requestView.id,
            ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name)
        )
        // Get version
        val versionView = articleVersionHelper.getVersion(2)
        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.EDITING)
        // Check
        assertEquals(expectedView, versionView)
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
    }

    @Test
    fun createReviewApproveProtocol() {
        // Creating request
        val requestView = reviewRequestHelper.makeRequest(2, ReviewRequestDTO(VersionDestination.PROTOCOL.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
        // Creating response
        val reviewView =
            reviewHelper.makeReview(2, requestView.id, ReviewDTO("edit please", ReviewResolution.APPROVE.name))
        // Get version
        val versionView = articleVersionHelper.getVersion(2)
        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_PROTOCOL)
        // Check
        assertEquals(expectedView, versionView)
        // Check available in search
        articleVersionHelper.existsOnce(
            ArticleSearchResult(2, "version 1.1", 1),
            SearchQueryDTO("version 1.1")
        )
    }

    @Test
    fun createReviewRequestChangesArticle() {
        // Creating request
        val requestView = reviewRequestHelper.makeRequest(2, ReviewRequestDTO(VersionDestination.ARTICLE.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
        // Creating response
        val reviewView = reviewHelper.makeReview(
            2,
            requestView.id,
            ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name)
        )
        // Get version
        val versionView = articleVersionHelper.getVersion(2)
        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.EDITING)
        // Check
        assertEquals(expectedView, versionView)
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
    }

    @Test
    fun createReviewApproveArticle() {
        // Creating request
        val requestView = reviewRequestHelper.makeRequest(2, ReviewRequestDTO(VersionDestination.ARTICLE.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
        // Creating response
        val reviewView = reviewHelper.makeReview(
            2,
            requestView.id,
            ReviewDTO("edit please", ReviewResolution.APPROVE.name)
        )
        // Get version
        val versionView = articleVersionHelper.getVersion(2)
        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_ARTICLE)
        // Check
        assertEquals(expectedView, versionView)
        // Check available in search
        assertTrue(
            articleVersionHelper.existsOnce(
                ArticleSearchResult(2, "version 1.1", 1),
                SearchQueryDTO("version 1.1")
            )
        )
    }
}
