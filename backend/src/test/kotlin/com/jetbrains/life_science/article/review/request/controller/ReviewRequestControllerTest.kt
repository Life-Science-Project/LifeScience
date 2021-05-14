package com.jetbrains.life_science.article.review.request.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.view.ReviewRequestView
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.section.view.SectionLazyView
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.view.ArticleVersionView
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class ReviewRequestControllerTest :
    ControllerTest<ReviewRequestDTO, ReviewRequestView>(ReviewRequestView::class.java) {

    init {
        apiUrl = "/api/review/request"
    }

    /**
     * Getting requests of the pending to review version.
     */
    @Test
    fun `get pending for review version's requests`() {
        // Preparing expected data
        val expectedVersion = ArticleVersionView(
            id = 4,
            name = "version 4.1",
            articleId = 1,
            sections = mutableListOf(),
            state = State.PENDING_FOR_REVIEW
        )
        val expectedRequest = listOf(
            ReviewRequestView(
                id = 1,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = ReviewView(
                    id = 5,
                    reviewRequestId = 1,
                    comment = "comment 5",
                    reviewerId = 1
                )
            ),
            ReviewRequestView(
                id = 2,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = null
            )
        )
        // Action
        val view = getAllRequests(4)

        // Check
        assertEquals(expectedRequest, view)
    }

    /**
     * Getting requests of the editing version.
     */
    @Test
    fun `get editing version's requests`() {
        // Preparing expected data
        val expectedVersion = ArticleVersionView(
            id = 6,
            name = "version 5.1",
            articleId = 2,
            sections = listOf(
                SectionLazyView(
                    id = 5,
                    name = "name 3",
                    order = 1
                )
            ),
            state = State.EDITING
        )
        val expectedRequest = listOf(
            ReviewRequestView(
                id = 4,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = ReviewView(
                    id = 2,
                    reviewRequestId = 4,
                    comment = "comment 2",
                    reviewerId = 1
                )
            ),
            ReviewRequestView(
                id = 5,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = ReviewView(
                    id = 3,
                    reviewRequestId = 5,
                    comment = "comment 3",
                    reviewerId = 1
                )
            ),
            ReviewRequestView(
                id = 6,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = ReviewView(
                    id = 4,
                    reviewRequestId = 6,
                    comment = "comment 4",
                    reviewerId = 1
                )
            )
        )
        // Action
        val view = getAllRequests(6)

        // Check
        assertEquals(expectedRequest, view)
    }

    /**
     * Should forbid getting requests of the published version.
     */
    @Test
    fun `get published version's requests`() {
        // Action and Check
        assertForbidden(getRequest(1, "/api/review/request/version/"))
    }

    /**
     * Getting active requests of the pending to review version.
     */
    @Test
    fun `get pending for review version's active requests`() {
        // Preparing expected data
        val expectedVersion = ArticleVersionView(
            id = 4,
            name = "version 4.1",
            articleId = 1,
            sections = mutableListOf(),
            state = State.PENDING_FOR_REVIEW
        )
        val expectedRequest = listOf(
            ReviewRequestView(
                id = 2,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = null
            )
        )
        // Action
        val view = getAllActiveRequests(4)

        // Check
        assertEquals(expectedRequest, view)
    }

    /**
     * Should forbid getting active requests of the published version.
     */
    @Test
    fun `get published version's active requests`() {
        // Action and Check
        assertForbidden(getRequest(1, "/api/review/request/version/active"))
    }

    /**
     * Should create new request.
     */
    @Test
    fun `create review request on editing version`() {
        // Preparing expected data
        val expectedVersion = ArticleVersionView(
            id = 6,
            name = "version 5.1",
            articleId = 2,
            sections = listOf(
                SectionLazyView(
                    id = 5,
                    name = "name 3",
                    order = 1
                )
            ),
            state = State.PENDING_FOR_REVIEW
        )
        val expectedRequest = ReviewRequestView(
            id = 8,
            destination = VersionDestination.ARTICLE,
            version = expectedVersion,
            resolution = null
        )
        val requestDTO = ReviewRequestDTO("ARTICLE")
        // Action
        val result = patch(6, requestDTO, "/api/review/request/version/")
        // Check
        assertEquals(expectedRequest, result)
    }

    /**
     * Should get Bad Request on creating new request on pending to review version.
     */
    @Test
    fun `create review request on pending to review version`() {
        // Preparing expected data
        val requestArticleDTO = ReviewRequestDTO("ARTICLE")
        val requestProtocolDTO = ReviewRequestDTO("PROTOCOL")
        // Action and Check
        assertBadRequest(
            "Review request for version 4 already exists",
            patchRequest(4, requestArticleDTO, "/api/review/request/version/")
        )
        assertBadRequest(
            "Review request for version 4 already exists",
            patchRequest(4, requestProtocolDTO, "/api/review/request/version/")
        )
    }

    /**
     * Should forbid creating new request on published version.
     */
    @Test
    fun `create review request on published version`() {
        // Preparing expected data
        val requestArticleDTO = ReviewRequestDTO("ARTICLE")
        val requestProtocolDTO = ReviewRequestDTO("PROTOCOL")
        // Action and Check
        assertForbidden(
            patchRequest(1, requestArticleDTO, "/api/review/request/version/")
        )
        assertForbidden(
            patchRequest(1, requestProtocolDTO, "/api/review/request/version/")
        )
    }

    /**
     * Should get Bad Request on creating new request with wrong destination.
     */
    @Test
    fun `create review request with wrong destination`() {
        // Preparing expected data
        val requestDTO = ReviewRequestDTO("PR0TOCOL")
        // Action and Check
        assertBadRequest(
            "Destination not exists",
            patchRequest(6, requestDTO, "/api/review/request/version/")
        )
    }

    /**
     * Should delete request without resolution.
     */
    @Test
    fun `delete existing request`() {
        // Preparing expected data
        val expectedVersion = ArticleVersionView(
            id = 4,
            name = "version 4.1",
            articleId = 1,
            sections = mutableListOf(),
            state = State.EDITING
        )
        val expectedRequest = listOf(
            ReviewRequestView(
                id = 1,
                destination = VersionDestination.ARTICLE,
                version = expectedVersion,
                resolution = ReviewView(
                    id = 5,
                    reviewRequestId = 1,
                    comment = "comment 5",
                    reviewerId = 1
                )
            )
        )
        // Action
        delete(2)
        val view = getAllRequests(4)
        // Check
        assertEquals(expectedRequest, view)
    }

    /**
     * Should get Bad request on deleting request with existing resolution.
     */
    @Test
    fun `delete request with existing resolution`() {
        // Action and Check
        assertBadRequest("This review request already proceed", deleteRequest(1))
    }

    /**
     * Should get 404 on deleting not existing request.
     */
    @Test
    fun `delete not existing request`() {
        // Action and Check
        assertNotFound("Review request", deleteRequest(666))
    }

    /**
     * Should forbid access to non-user versions and allow otherwise
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        // Prepare request data
        val requestDTO = ReviewRequestDTO("PROTOCOL")

        // Requests with Check
        assertOk(getRequest(4, "/api/review/request/version/"))
        assertForbidden(getRequest(6, "/api/review/request/version/"))

        assertOk(getRequest(4, "/api/review/request/version/active/"))
        assertForbidden(getRequest(6, "/api/review/request/version/active/"))

        assertOk(deleteRequest(2))
        assertForbidden(deleteRequest(4))

        assertOk(patchRequest(4, requestDTO, "/api/review/request/version/"))
        assertForbidden(patchRequest(1, requestDTO, "/api/review/request/version/"))
    }

    /**
     * Should forbid access to requests
     */
    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        // Prepare request data
        val requestDTO = ReviewRequestDTO("PROTOCOL")

        // Requests with Check
        assertUnauthenticated(getRequest(1, "/api/review/request/version/"))
        assertUnauthenticated(getRequest(1, "/api/review/request/version/active/"))
        assertUnauthenticated(patchRequest(1, requestDTO, "/api/review/request/version/"))
        assertUnauthenticated(deleteRequest(1))
    }

    private fun getAllRequests(versionId: Long): List<ReviewRequestView> {
        val request = mockMvc.get("/api/review/request/version/$versionId").andReturn().response.contentAsString
        return getViewsFromJson(request, ReviewRequestView::class.java)
    }

    private fun getAllActiveRequests(versionId: Long): List<ReviewRequestView> {
        val request = mockMvc.get("/api/review/request/version/active/$versionId").andReturn().response.contentAsString
        return getViewsFromJson(request, ReviewRequestView::class.java)
    }
}
