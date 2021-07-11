package com.jetbrains.life_science.exception.auth

import com.jetbrains.life_science.exception.ApiException

class ForbiddenOperationException : ApiException(
    httpCode = 403,
    code = 403_000
)
