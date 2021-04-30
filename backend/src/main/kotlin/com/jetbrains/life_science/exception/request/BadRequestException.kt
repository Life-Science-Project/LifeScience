package com.jetbrains.life_science.exception.request

class BadRequestException(override val message: String) : RuntimeException(message)
