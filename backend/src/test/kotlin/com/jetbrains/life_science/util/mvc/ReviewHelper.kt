package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.view.ReviewView
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class ReviewHelper(
    private val mvc: MockMvc
) : BaseTestHelper() {

    fun makeReview(versionId: Long, requestId: Long, dto: ReviewDTO): ReviewView {
        val json = mvc.post("/api/articles/versions/$versionId/reviews/request/$requestId") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }.andReturn().response.contentAsString
        return jsonMapper.readValue(json, ReviewView::class.java)
    }
}
