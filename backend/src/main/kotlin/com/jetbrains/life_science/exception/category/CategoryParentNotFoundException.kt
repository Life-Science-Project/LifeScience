package com.jetbrains.life_science.exception.category

import com.jetbrains.life_science.exception.ApiException

class CategoryParentNotFoundException(parentId: Long) : ApiException(
    httpCode = 404,
    code = 404_002,
    arguments = listOf(listOf(parentId.toString()))
)