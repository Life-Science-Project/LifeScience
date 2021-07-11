package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.handler.ApiExceptionView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SecurityRestControllerAdvisor {

    /**
     * Handle access denied by user's role
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(exception: AccessDeniedException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                code = 403_000,
            ),
            HttpStatus.FORBIDDEN
        )
    }
}
