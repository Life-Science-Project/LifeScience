package com.jetbrains.life_science.exception.request

/**
 * @author Потапов Александр
 * @since 06.05.2021
 */
class ReviewResponseAlreadyExistsException(override val message: String) : RuntimeException(message)
