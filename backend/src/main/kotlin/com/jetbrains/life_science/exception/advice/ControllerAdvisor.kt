package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.*
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.exception.request.UserAlreadyExistsException
import com.jetbrains.life_science.exception.request.ContentIsNotEditableException
import com.jetbrains.life_science.exception.ApiErrorResponse
import com.jetbrains.life_science.exception.ArticleNotEmptyException
import com.jetbrains.life_science.exception.not_found.*
import org.springframework.expression.AccessException
import com.jetbrains.life_science.exception.request.*
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ControllerAdvisor : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CategoryNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCategoryNotFound(ex: CategoryNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Category")
    }

    @ExceptionHandler(CategoryNotEmptyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCategoryNotEmpty(ex: CategoryNotEmptyException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse("Category not empty")
    }

    @ExceptionHandler(SectionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleSectionNotFound(ex: SectionNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Section")
    }

    @ExceptionHandler(ContentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleContentNotFound(ex: ContentNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Content")
    }

    @ExceptionHandler(ArticleVersionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleArticleVersionNotFound(ex: ArticleVersionNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Article version")
    }

    @ExceptionHandler(PublishedVersionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePublishedVersionNotFoundException(
        ex: PublishedVersionNotFoundException,
        request: WebRequest
    ): ApiErrorResponse {
        return notFoundResponse("Published version")
    }

    @ExceptionHandler(ArticleNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleArticleNotFound(ex: ArticleNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Article")
    }

    @ExceptionHandler(ArticleNotEmptyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCategoryNotEmpty(ex: ArticleNotEmptyException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse("Article not empty")
    }

    @ExceptionHandler(ReviewNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleReviewNotFound(ex: ReviewNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("Review")
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserAlreadyExists(ex: UserAlreadyExistsException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse("User already exists")
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFound(ex: UserNotFoundException, request: WebRequest): ApiErrorResponse {
        return notFoundResponse("User")
    }

    @ExceptionHandler(ContentIsNotEditableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleContentIsNotEditable(ex: ContentIsNotEditableException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse(ex.message)
    }

    @ExceptionHandler(AccessException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleIllegalAccess(ex: AccessException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse(ex.message)
    }

    @ExceptionHandler(IllegalAccessException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleIllegalAccess(ex: IllegalAccessException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse(ex.message)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(ex: BadRequestException, request: WebRequest): ApiErrorResponse {
        return ApiErrorResponse(ex.message)
    }

    @ExceptionHandler(SearchUnitTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleSearchUnitTypeNotSupported(
        ex: SearchUnitTypeNotSupportedException,
        request: WebRequest
    ): ApiErrorResponse {
        return ApiErrorResponse("Search unit with type ${ex.unsupportedType} not supported, [ARTICLE, CONTENT] only available.")
    }

    private fun notFoundResponse(entity: String): ApiErrorResponse {
        return ApiErrorResponse("$entity not found")
    }
}
