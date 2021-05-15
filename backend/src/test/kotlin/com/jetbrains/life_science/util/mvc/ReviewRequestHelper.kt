package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.view.ReviewRequestView
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch

class ReviewRequestHelper(
    mvc: MockMvc
) : BaseTestHelper(mvc) {

    fun makeRequest(versionId: Long, dto: ReviewRequestDTO): ReviewRequestView? {
        return patch("/api/review/request/version/$versionId", dto, ReviewRequestView::class.java)
    }
}
