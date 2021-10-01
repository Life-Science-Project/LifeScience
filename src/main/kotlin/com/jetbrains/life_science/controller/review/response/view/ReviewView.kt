package com.jetbrains.life_science.controller.review.response.view

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.jetbrains.life_science.controller.user.view.UserShortView
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import java.time.LocalDateTime

data class ReviewView(
    val id: Long,
    val comment: String,
    val resolution: ReviewResolution,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val reviewer: UserShortView?
)
