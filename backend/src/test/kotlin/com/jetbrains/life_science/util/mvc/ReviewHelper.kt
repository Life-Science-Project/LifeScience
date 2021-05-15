package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.view.ReviewView
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post

class ReviewHelper(
    mvc: MockMvc
) : BaseTestHelper(mvc) {

    private val viewToken = ReviewView::class.java

    fun makeReview(versionId: Long, requestId: Long, dto: ReviewDTO): ReviewView? {
        return post("/api/articles/versions/$versionId/reviews/request/$requestId", dto, viewToken)
    }

    fun makeReviewRaw(versionId: Long, requestId: Long, dto: ReviewDTO): ResultActionsDsl {
        return request(HttpMethod.POST, "/api/articles/versions/$versionId/reviews/request/$requestId", dto)
    }

    fun getReview(versionId: Long, requestId: Long): ReviewView? {
        return getView("/api/articles/versions/$versionId/reviews/requests/$requestId", viewToken)
    }

    fun getAllReviews(versionId: Long): List<ReviewView> {
        return getViews("/api/articles/versions/$versionId/reviews", viewToken)
    }

    fun assertUnauthorizedGetDenied(versionId: Long, requestId: Long) {
        assertIsUnauthorized(request(HttpMethod.GET, "/api/articles/versions/$versionId/reviews/requests/$requestId"))
    }

    fun assertUnauthorizedGetAllDenied(versionId: Long) {
        assertIsUnauthorized(request(HttpMethod.GET, "/api/articles/versions/$versionId/reviews"))
    }

    fun assertUnauthorizedPostDenied(versionId: Long, requestId: Long, dto: ReviewDTO) {
        assertIsUnauthorized(request(HttpMethod.PATCH, "/api/articles/versions/$versionId/reviews/request/$requestId", dto))
    }
}
