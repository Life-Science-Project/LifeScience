package com.jetbrains.life_science.exception.validator

class ValidationFailedException(override val message: String) : RuntimeException(message)
