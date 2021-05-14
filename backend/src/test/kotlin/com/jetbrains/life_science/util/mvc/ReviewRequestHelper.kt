package com.jetbrains.life_science.util.mvc

import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.view.ReviewRequestView
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch

class ReviewRequestHelper(
    private val mvc: MockMvc
) : BaseTestHelper() {

    fun makeRequest(versionId: Long, dto: ReviewRequestDTO): ReviewRequestView {
        val json = mvc.patch("/api/review/request/version/$versionId") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(dto)
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }.andReturn().response.contentAsString
        return jsonMapper.readValue(json, ReviewRequestView::class.java)
    }
}
