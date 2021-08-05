package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.exception.protocol.PublicProtocolNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ProtocolControllerAdvisor {

    @ExceptionHandler(DraftProtocolNotFoundException::class)
    fun handleDraftProtocolNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                systemCode = 404007
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(PublicProtocolNotFoundException::class)
    fun handlePublicProtocolNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                systemCode = 404007
            ),
            HttpStatus.NOT_FOUND
        )
    }
}
