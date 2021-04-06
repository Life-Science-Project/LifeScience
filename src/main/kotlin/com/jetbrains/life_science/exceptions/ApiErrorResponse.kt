package com.jetbrains.life_science.exceptions

import java.time.LocalDateTime

class ApiErrorResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
