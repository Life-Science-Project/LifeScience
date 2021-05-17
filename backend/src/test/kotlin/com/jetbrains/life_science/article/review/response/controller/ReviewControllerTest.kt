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

    lateinit var searchHelper: SearchHelper

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("content", "elastic/content.json")
            addPopulator("content_version", "elastic/content_version.json")
            addPopulator("article", "elastic/article.json")
            addPopulator("section", "elastic/section.json")
        }
        searchHelper = SearchHelper(mockMvc)
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * The test checks that an unregistered user is not able to work with the review
     */
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

    /**
     * Test checks 404 code when requesting a non-existent review
     */
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

    /**
     * The test checks that a regular user receives a review for their versions
     */
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

    /**
     * The test checks that the admin receives all reviews
     */
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

    /**
     * The test checks the receipt of the review after its creation
     */
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

    /**
     * The test verifies the scenario
     *
     *  - Create a request for the publication of the protocol
     *  - Make sure that the version is not published
     *  - A review is created to make changes
     *  - Make sure that the version has changed its status to "edit"
     *  - Make sure that the version is not available in the search
     */
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
        print("my_check" + searchHelper.getSearchResults(SearchQueryDTO("version 1.1")))
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

    /**
     * The test verifies the scenario
     *
     *  - Create a request for the publication of the protocol
     *  - Make sure that the version is not published
     *  - A review is created to make changes
     *  - Make sure that the version has changed its status to "approved"
     *  - Make sure that the version is available in the search
     */
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
        assertExistsOnceInSearch(SearchQueryDTO("version 1.1"), ArticleSearchResult(2, "version 1.1", 1))
    }

    /**
     * The test verifies the scenario
     *
     *  - Create a request for the publication of the article
     *  - Make sure that the version is not published
     *  - A review is created to make changes
     *  - Make sure that the version has changed its status to "edit"
     *  - Make sure that the version is not available in the search
     */
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

    /**
     * The test verifies the scenario
     *
     *  - Create a review for the publication of the protocol
     *  - Make sure that the version is not published
     *  - A review is created to make changes
     *  - Make sure that the version has changed its status to "approved"
     *  - Make sure that the version is available in the search
     */
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
        println(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")))
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
        assertExistsOnceInSearch(SearchQueryDTO("version 1.1"), ArticleSearchResult(2, "version 1.1", 1))
    }

    /**
     * The test checks that a 404 code is received when trying to review a non-existent request
     */
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

    /**
     * The test checks to receive a 404 code when trying to review a non-existent version
     */
    @Test
    fun `attempt to review to wrong version id`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.ARTICLE.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check
        assertNotFound(
            "Article version",
            postRequest(
                ReviewDTO("ok", ReviewResolution.APPROVE.name),
                "/api/articles/versions/10000/reviews/request/${requestView.id}"
            )
        )
    }

    /**
     * The test verifies the scenario:
     * - Create request 1 to publish the protocol
     * - Create request 2 to publish the protocol
     * - Make sure that both versions are not published
     * - Review 1 is created for confirmation
     * - Review 2 is created for confirmation
     * - Make sure that both versions have changed their status to "published"
     * - Make sure that both versions are available in the search
     */
    @Test
    fun `two protocols creation test`() {
        // Creating request 1
        val requestView1 = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Creating request 2
        val requestView2 = patch(
            3,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Check both are not available in search
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 1.1")).isEmpty())
        assertTrue(searchHelper.getSearchResults(SearchQueryDTO("version 2.1")).isEmpty())

        // Approve both of them
        post(
            ReviewDTO("ok1", ReviewResolution.APPROVE.name),
            "/api/articles/versions/2/reviews/request/${requestView1.id}"
        )
        post(
            ReviewDTO("ok2", ReviewResolution.APPROVE.name),
            "/api/articles/versions/3/reviews/request/${requestView2.id}"
        )

        // Get versions
        val versionView1 = get(2, ArticleVersionView::class.java, "/api/articles/versions")
        val versionView2 = get(3, ArticleVersionView::class.java, "/api/articles/versions")

        // Creating expected results
        val expectedView1 = ArticleVersionView(2, "version 1.1", 1, emptyList(), State.PUBLISHED_AS_PROTOCOL)
        val expectedView2 = ArticleVersionView(
            3,
            "version 2.1",
            1,
            listOf(SectionLazyView(id = 4, name = "name 2", order = 3)),
            State.PUBLISHED_AS_PROTOCOL
        )
        // Check
        assertEquals(expectedView1, versionView1)
        assertEquals(expectedView2, versionView2)

        // Check available in search
        assertExistsOnceInSearch(SearchQueryDTO("version 1.1"), ArticleSearchResult(2, "version 1.1", 1))
        assertExistsOnceInSearch(SearchQueryDTO("version 2.1"), ArticleSearchResult(3, "version 2.1", 1))
    }

    /**
     * The test checks that the code 400 is received when trying to give a review twice for one request
     */
    @Test
    fun `two reviews error check test`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        // Creating response
        val dto = ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name)
        post(dto, "/api/articles/versions/2/reviews/request/${requestView.id}")
        // Creating duplicate response
        assertBadRequest(
            "Review to request already exists",
            postRequest(dto, "/api/articles/versions/2/reviews/request/${requestView.id}")
        )
    }

    /**
     * The test verifies that code 400 is received when the ID of the article version from the request and the submitted
     */
    @Test
    fun `invalid version and request check test`() {
        // Creating request
        val requestView = patch(
            2,
            ReviewRequestDTO(VersionDestination.PROTOCOL.name),
            "/api/review/request/version/",
            ReviewRequestView::class.java
        )
        val dto = ReviewDTO("edit please", ReviewResolution.CHANGES_REQUESTED.name)
        // Creating response with wrong version id
        assertBadRequest(
            "Request version id and version id do not matches",
            postRequest(dto, "/api/articles/versions/3/reviews/request/${requestView.id}")
        )
    }

    private fun assertExistsOnceInSearch(searchQueryDTO: SearchQueryDTO, result: ArticleSearchResult) {
        val hits2 = searchHelper.countHits(searchQueryDTO, result)
        assertEquals(1, hits2)
    }
}
