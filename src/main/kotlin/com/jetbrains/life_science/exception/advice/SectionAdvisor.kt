package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SectionAdvisor {
    @ExceptionHandler(SectionNotFoundException::class)
    fun sectionNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(
                systemCode = 404006
            ),
            HttpStatus.NOT_FOUND
        )
    }
}
