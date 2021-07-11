package com.jetbrains.life_science.exception.category

import com.jetbrains.life_science.exception.ApiException

class CategoryNoParentsException: ApiException(
    httpCode = 400,
    code = 400_002
)