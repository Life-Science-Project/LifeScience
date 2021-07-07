package com.jetbrains.life_science.exception.auth

import com.jetbrains.life_science.exception.ApiException

class InvalidAccessTokenException : ApiException(
    httpCode = 401,
    code = 401_011
)