package com.jetbrains.life_science.exception

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@RestControllerAdvice
class ControllerAdvisor : ResponseEntityExceptionHandler() {

    @ExceptionHandler(SectionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleSectionNotFound(ex: SectionNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Section")
    }

    @ExceptionHandler(ParagraphNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleParagraphNotFound(ex: ParagraphNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Paragraph")
    }

    @ExceptionHandler(ArticleVersionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleArticleVersionNotFound(ex: ArticleNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Article")
    }

    @ExceptionHandler(ArticleNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleArticleNotFound(ex: ArticleNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Article")
    }

    @ExceptionHandler(ReviewNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleReviewNotFound(ex: ReviewNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Review")
    }

    private fun notFoundResponse(entity: String): ApiErrorResponse {
        return ApiErrorResponse("$entity not found")
    }
}
