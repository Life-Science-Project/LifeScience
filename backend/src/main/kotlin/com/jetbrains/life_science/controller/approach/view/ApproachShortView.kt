package com.jetbrains.life_science.controller.approach.view

import java.time.LocalDateTime

data class ApproachShortView(
    val id: Long,
    val name: String,
    val creationDate: LocalDateTime,
    val tags: List<String>
)