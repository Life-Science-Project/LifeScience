package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.category.CategoryNotFoundException
import com.jetbrains.life_science.exception.category.CategoryParentNotFoundException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.maker.makeExceptionView
import com.jetbrains.life_science.exception.not_found.DegreeNotFoundException
import com.jetbrains.life_science.exception.not_found.ApproachNotFoundException
import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NotFoundAdvisor {

    @ExceptionHandler(CategoryNotFoundException::class)
    fun handleCategoryNotFoundException(exception: CategoryNotFoundException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(404_001, exception.categoryId),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(CategoryParentNotFoundException::class)
    fun handleCategoryParentNotFoundException(exception: CategoryParentNotFoundException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(404_002, exception.parentId),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(ApproachNotFoundException::class)
    fun handleDraftApproachNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(systemCode = 404_003),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(SectionNotFoundException::class)
    fun sectionNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(systemCode = 404_006),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(ProtocolNotFoundException::class)
    fun handleDraftProtocolNotFound(): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            ApiExceptionView(systemCode = 404_007),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(DegreeNotFoundException::class)
    fun handleDegreeNotFound(exception: DegreeNotFoundException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(404_008, listOf(exception.message)),
            HttpStatus.NOT_FOUND
        )
    }
}
