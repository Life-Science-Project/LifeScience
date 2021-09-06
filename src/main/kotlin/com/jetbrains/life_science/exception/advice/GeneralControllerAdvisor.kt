package com.jetbrains.life_science.exception.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.exception.common.WrongRequestWithMessageException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.maker.makeExceptionView
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GeneralControllerAdvisor {

    /**
     * Handling non-existent dto fields
     */
    @ExceptionHandler(MissingKotlinParameterException::class)
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
            ApiExceptionView(403_000),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(ForbiddenOperationException::class)
    fun handleForbiddenOperationException(exception: ForbiddenOperationException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(403_000),
            HttpStatus.FORBIDDEN
        )
    }

    /**
     * Handling validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ApiExceptionView> {
        exception.bindingResult.fieldErrors.joinToString(",") { it.field }
        return ResponseEntity(
            makeExceptionView(400_005, exception.fieldError?.defaultMessage.toString()),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MismatchedInputException::class)
    fun handleInvalidJSON(exception: MismatchedInputException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(400_999),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(WrongRequestWithMessageException::class)
    fun handleWrongRequestWithMessageException(exception: WrongRequestWithMessageException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(400_999, exception.message),
            HttpStatus.BAD_REQUEST
        )
    }
}
