package com.jetbrains.life_science.exception.category

import com.jetbrains.life_science.exception.ApiException

class CategoryNotFoundException(categoryId: Long) : ApiException(
    httpCode = 404,
    code = 404_001,
    arguments = listOf(listOf(categoryId.toString()))
)