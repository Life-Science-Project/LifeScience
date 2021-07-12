package com.jetbrains.life_science.exception.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.maker.makeExceptionView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GeneralControllerAdvisor {

    /**
     * Handling non-existent dto fields
     */
    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingKotlinParameterException(
        exception: MissingKotlinParameterException
    ): ResponseEntity<ApiExceptionView> {
        val fieldName = exception.path.joinToString(",") { it.fieldName }
        return ResponseEntity(
            makeExceptionView(400_001, fieldName),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handling incorrect dto field types
     */
    @ExceptionHandler(InvalidFormatException::class)
    fun handleJsonToDtoBindingException(
        exception: InvalidFormatException
    ): ResponseEntity<ApiExceptionView> {
        val fieldName = exception.path.joinToString(",") { it.fieldName }
        val errorValue = exception.value.toString()
        return ResponseEntity(
            makeExceptionView(400_003, fieldName, errorValue),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handle access denied by user's role
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                403_000
            ),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(ForbiddenOperationException::class)
    fun handleForbiddenOperationException(exception: ForbiddenOperationException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                403_000
            ),
            HttpStatus.FORBIDDEN
        )
    }

    /**
     * Handling validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ApiExceptionView> {
        val fieldName = exception.bindingResult.fieldErrors.joinToString(",") { it.field }
        return ResponseEntity(
            makeExceptionView(400_993, fieldName),
            HttpStatus.BAD_REQUEST
        )
    }
}
