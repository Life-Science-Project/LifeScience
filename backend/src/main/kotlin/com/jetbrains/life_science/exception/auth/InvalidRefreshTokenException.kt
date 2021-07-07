package com.jetbrains.life_science.exception.auth

import com.jetbrains.life_science.exception.ApiException

class InvalidRefreshTokenException : ApiException(
    httpCode = 400,
    code = 400_001
)
