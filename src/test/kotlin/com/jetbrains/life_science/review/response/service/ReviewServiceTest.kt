package com.jetbrains.life_science.review.response.service

import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.review.response.service.maker.makeReviewInfo
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql(value = ["/scripts/initial_data.sql", "/scripts/review/response/review_data.sql"])
@Transactional
class ReviewServiceTest {

    @Autowired
    lateinit var service: ReviewService

    @Autowired
    lateinit var credentialsService: CredentialsService

    /**
     * Should create new review
     */
    @Test
    fun `create new review`() {
        // Prepare data
        val credentials = credentialsService.getById(1L)
        val info = makeReviewInfo(
            id = 0,
            comment = "Review comment",
            resolution = ReviewResolution.CHANGES_REQUESTED,
            reviewer = credentials
        )

        // Action
        val createdReview = service.createReview(info)
        val review = service.getReview(createdReview.id)

        // Assert
        assertEquals(info.comment, review.comment)
        assertEquals(info.date, review.date)
        assertEquals(info.resolution, review.resolution)
        assertEquals(info.reviewer.id, review.reviewer.id)
    }

    /**
     * Should get existing review
     */
    @Test
    fun `get existing review`() {
        // Prepare data
        val reviewId = 1L
        val expectedOwnerId = 1L
        val expectedComment = "my first review"
        val expectedResolution = ReviewResolution.APPROVE

        // Action
        val review = service.getReview(reviewId)

        // Assert
        assertEquals(expectedComment, review.comment)
        assertEquals(expectedResolution, review.resolution)
        assertEquals(expectedOwnerId, review.reviewer.id)
    }

    /**
     * Should throw ReviewNotFoundException exception
     */
    @Test
    fun `get non-existing review`() {
        // Prepare data
        val reviewId = 666L

        // Action & Assert
        assertThrows<ReviewNotFoundException> {
            service.getReview(reviewId)
        }
    }
}
