package com.jetbrains.life_science.controller.category.view

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import java.time.LocalDateTime

data class CategoryView(
    val name: String,
    val aliases: List<String>,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val creationDate: LocalDateTime,
    val subCategories: List<CategoryShortView>,
    val approaches: List<ApproachShortView>
)
