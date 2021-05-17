package com.jetbrains.life_science.article.review.response.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.view.ReviewRequestView
import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.section.view.SectionLazyView
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
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
@AutoConfigureMockMvc
internal class ReviewControllerTest :
    ControllerTest<ReviewDTO, ReviewView>(ReviewView::class.java) {

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
    @WithAnonymousUser
    fun `anonymous privileges`() {
        assertUnauthenticated(getRequest("/api/articles/versions/2/reviews"))
        assertUnauthenticated(getRequest("/api/articles/versions/2/reviews/requests/1"))
        assertUnauthenticated(
            postRequest(
                ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name),
                "/api/articles/versions/2/reviews/request/1"
            )
        )
    }

    @Test
    fun `get non-existing review test`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Creating review
        assertNotFound("Review", getRequest(requestView.id, "/api/articles/versions/2/reviews/requests/"))
    }

    @Test
    @WithUserDetails("user")
    fun `get all reviews test for user`() {
        // Getting reviews
        val reviewViews = getViewsFromJson(getRequest("/api/articles/versions/4/reviews"), ReviewView::class.java)

        // Creating expected data
        val expectedReviewViews = listOf(
            ReviewView(5, 1, "comment 5", 1),
        )
        // Check
        assertEquals(expectedReviewViews, reviewViews)
    }

    @Test
    fun `get all reviews test`() {
        // Getting reviews
        val reviewViews = getViewsFromJson(getRequest("/api/articles/versions/6/reviews"), ReviewView::class.java)
        // Creating expected data
        val expectedReviewViews = listOf(
            ReviewView(2, 4, "comment 2", 1),
            ReviewView(3, 5, "comment 3", 1),
            ReviewView(4, 6, "comment 4", 1)
        )
        // Check
        assertEquals(expectedReviewViews, reviewViews)
    }

    @Test
    fun `get created review test`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )

        // Creating review
        val reviewCreatedView = post(
            ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name),
            "/api/articles/versions/2/reviews/request/${requestView.id}"
        )

        // Getting review
        val reviewView = get(requestView.id, "/api/articles/versions/2/reviews/requests")
        // Creating expected data
        val expectedView = ReviewView(reviewCreatedView!!.id, requestView.id, "edit please", 1)
        // Check
        assertEquals(expectedView, reviewCreatedView)
        assertEquals(expectedView, reviewView)
    }

    @Test
    fun `create review to make changes for protocol`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())

        // Creating response
        val reviewView = post(
            ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name),
            "/api/articles/versions/2/reviews/request/${requestView.id}"
        )

        // Get version
        val versionView = get(2, ArticleVersionView::class.java, "/api/articles/versions")

        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.EDITING)
        // Check
        assertEquals(expectedView, versionView)
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())
    }

    @Test
    fun `create review to approve protocol`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())

        // Creating response
        val reviewView = post(
            ReviewDTO("edit please", ReviewResolution.APPROVE.name),
            "/api/articles/versions/2/reviews/request/${requestView.id}"
        )

        // Get version
        val versionView = get(2, ArticleVersionView::class.java, "/api/articles/versions")

        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_PROTOCOL)
        // Check
        assertEquals(expectedView, versionView)

        // Check available in search
        val expectedSearchResult = ArticleSearchResult(2, "version 1.1", 1)
        val hits = searchHelper.countHits(SearchQueryDTO("version 1.1"), expectedSearchResult)
        assertEquals(1, hits)
    }

    @Test
    fun `create review to request changes for article`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.ARTICLE.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())

        // Creating response
        val reviewView = post(
            ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name),
            "/api/articles/versions/2/reviews/request/${requestView.id}"
        )

        // Get version
        val versionView = get(2, ArticleVersionView::class.java, "/api/articles/versions")

        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.EDITING)
        // Check
        assertEquals(expectedView, versionView)
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())
    }

    @Test
    fun `create review to approve article`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.ARTICLE.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())

        // Creating response
        val reviewView = post(
            ReviewDTO("edit please", ReviewResolution.APPROVE.name),
            "/api/articles/versions/2/reviews/request/${requestView.id}"
        )

        // Get version
        val versionView = get(2, ArticleVersionView::class.java, "/api/articles/versions")

        // Creating expected result
        val expectedView = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_ARTICLE)
        // Check
        assertEquals(expectedView, versionView)

        // Check available in search
        val expectedSearchResult = ArticleSearchResult(2, "version 1.1", 1)
        val hits = searchHelper.countHits(SearchQueryDTO("version 1.1"), expectedSearchResult)
        assertEquals(1, hits)
    }

    @Test
    fun `attempt to review to wrong request id`() {

        assertNotFound(
            "Review request",
            postRequest(
                ReviewDTO("ok", ReviewResolution.APPROVE.name),
                "/api/articles/versions/1/reviews/request/1000"
            )
        )
    }

    @Test
    fun `attempt to review to wrong version id`() {
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.ARTICLE.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )

        assertNotFound(
            "Article version",
            postRequest(
                ReviewDTO("ok", ReviewResolution.APPROVE.name),
                "/api/articles/versions/10000/reviews/request/${requestView.id}"
            )
        )
    }

    @Test
    fun `two protocols creation test`() {
        // Creating request
        val requestView1 = reviewRequestHelper.makeRequest(2, ReviewRequestDTO(VersionDestination.PROTOCOL.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 1.1")))
        // Creating request1
        val requestView2 = reviewRequestHelper.makeRequest(3, ReviewRequestDTO(VersionDestination.PROTOCOL.name))
        // Check not available in search
        assertTrue(articleVersionHelper.notExistsInSearch(SearchQueryDTO("version 2.1")))
        // Creating response
        val reviewView1 =
            reviewHelper.makeReview(2, requestView1!!.id, ReviewDTO("ok1", ReviewResolution.APPROVE.name))
        val reviewView2 =
            reviewHelper.makeReview(3, requestView2!!.id, ReviewDTO("ok2", ReviewResolution.APPROVE.name))
        // Get version1
        val versionView1 = articleVersionHelper.getVersion(2)
        // Creating expected result
        val expectedView1 = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_PROTOCOL)
        // Check
        assertEquals(expectedView1, versionView1)
        // Get version2
        val versionView2 = articleVersionHelper.getVersion(3)
        // Creating expected result
        val expectedView2 = ArticleVersionView(
            3,
            "version 2.1",
            1,
            listOf(SectionLazyView(4, "name 2", 3)),
            State.PUBLISHED_AS_PROTOCOL
        )
        // Check
        assertEquals(expectedView2, versionView2)
        // Check 1 available in search
        articleVersionHelper.existsOnce(
            ArticleSearchResult(2, "version 1.2", 1),
            SearchQueryDTO("version 1.2")
        )
        // Check 2 available in search
        articleVersionHelper.existsOnce(
            ArticleSearchResult(2, "version 2.1", 1),
            SearchQueryDTO("version 2.1")
        )
    }
}
