package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.not_found.DraftApproachNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApproachControllerAdvisor {

    @ExceptionHandler(DraftApproachNotFoundException::class)
    fun handleDraftApproachNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                systemCode = 404003
            ),
            HttpStatus.NOT_FOUND
        )
    }
}
