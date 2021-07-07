package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.ApiErrorResponse
import com.jetbrains.life_science.exception.auth.ExpiredRefreshTokenException
import com.jetbrains.life_science.exception.auth.InvalidRefreshTokenException
import com.jetbrains.life_science.exception.auth.RefreshTokenNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthControllerAdvisor {

    @ExceptionHandler(RefreshTokenNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRefreshTokenNotFound(exception: RefreshTokenNotFoundException): ApiErrorResponse {
        return ApiErrorResponse("No refresh token found, please log in")
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidRefreshToken(exception: InvalidRefreshTokenException): ApiErrorResponse {
        return ApiErrorResponse("Refresh token is invalid, please log in")
    }

    @ExceptionHandler(ExpiredRefreshTokenException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleExpiredRefreshTokenException(exception: ExpiredRefreshTokenException): ApiErrorResponse {
        return ApiErrorResponse("Refresh token expired, please log in")
    }
}
