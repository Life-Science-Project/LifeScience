package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.auth.ExpiredAccessTokenException
import com.jetbrains.life_science.exception.auth.ExpiredRefreshTokenException
import com.jetbrains.life_science.exception.auth.InvalidAccessTokenException
import com.jetbrains.life_science.exception.auth.InvalidCredentialsException
import com.jetbrains.life_science.exception.auth.InvalidRefreshTokenException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.maker.makeExceptionView
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthorizationControllerAdvisor {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(exception: UserAlreadyExistsException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(400_004, exception.email),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    fun handleInvalidRefreshTokenException(exception: InvalidRefreshTokenException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(401_001),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(ExpiredRefreshTokenException::class)
    fun handleExpiredRefreshTokenException(exception: ExpiredRefreshTokenException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(401_002),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(InvalidAccessTokenException::class)
    fun handleInvalidAccessTokenException(exception: InvalidAccessTokenException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(401_003),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(ExpiredAccessTokenException::class)
    fun handleExpiredAccessTokenException(exception: ExpiredAccessTokenException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(401_004),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(exception: InvalidCredentialsException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(401_005),
            HttpStatus.UNAUTHORIZED
        )
    }
}
