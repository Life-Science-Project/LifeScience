package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.approach.draft.DraftApproachNotFoundException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApproachControllerAdvisor {


    @ExceptionHandler(DraftApproachNotFoundException::class)
    fun handleDraftApproachNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            HttpStatus.NOT_FOUND, ApiExceptionView(
                code =
            )
        )
    }

}