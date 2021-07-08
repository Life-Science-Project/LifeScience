package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.ApiException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiRestControllerAdvisor {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ResponseEntity<ApiExceptionView> {
        val httpStatus = HttpStatus.valueOf(exception.httpCode)
        return ResponseEntity(
            ApiExceptionView(exception.code, exception.arguments),
            httpStatus
        )
    }
}
