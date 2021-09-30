package com.jetbrains.life_science.controller.review.request.view

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.review.response.view.ReviewView
import com.jetbrains.life_science.controller.user.view.UserShortView
import com.jetbrains.life_science.review.request.entity.RequestState
import java.time.LocalDateTime

data class ApproachReviewRequestView(
    val id: Long,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val state: RequestState,
    val reviews: List<ReviewView>,
    val editor: UserShortView?,
    val approach: ApproachShortView
)
