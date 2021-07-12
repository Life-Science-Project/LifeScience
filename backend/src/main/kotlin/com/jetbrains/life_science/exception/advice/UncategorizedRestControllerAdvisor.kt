// package com.jetbrains.life_science.exception.advice
//
// import com.jetbrains.life_science.exception.ApiErrorResponse
// import com.jetbrains.life_science.exception.UnauthorizedException
// import com.jetbrains.life_science.exception.not_empty.ArticleNotEmptyException
// import com.jetbrains.life_science.exception.not_empty.CategoryNotEmptyException
// import com.jetbrains.life_science.exception.not_found.*
// import com.jetbrains.life_science.exception.request.*
// import org.springframework.http.HttpStatus
// import org.springframework.web.bind.annotation.ExceptionHandler
// import org.springframework.web.bind.annotation.ResponseStatus
// import org.springframework.web.bind.annotation.RestControllerAdvice
// import org.springframework.web.context.request.WebRequest
// import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
//
// @RestControllerAdvice
// class UncategorizedRestControllerAdvisor : ResponseEntityExceptionHandler() {
//
//     @ExceptionHandler(CategoryNotEmptyException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleCategoryNotEmpty(ex: CategoryNotEmptyException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse("Category not empty")
//     }
//
//     @ExceptionHandler(SectionNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleSectionNotFound(ex: SectionNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Section")
//     }
//
//     @ExceptionHandler(ContentNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleContentNotFound(ex: ContentNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Content")
//     }
//
//     @ExceptionHandler(ArticleVersionNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleArticleVersionNotFound(ex: ArticleVersionNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Article version")
//     }
//
//     @ExceptionHandler(PublishedVersionNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handlePublishedVersionNotFoundException(
//         ex: PublishedVersionNotFoundException,
//         request: WebRequest
//     ): ApiErrorResponse {
//         return notFoundResponse("Published version")
//     }
//
//     @ExceptionHandler(ArticleNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleArticleNotFound(ex: ArticleNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Article")
//     }
//
//     @ExceptionHandler(ArticleNotEmptyException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleCategoryNotEmpty(ex: ArticleNotEmptyException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse("Article not empty")
//     }
//
//     @ExceptionHandler(ReviewNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleReviewNotFound(ex: ReviewNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Review")
//     }
//
//     @ExceptionHandler(UserAlreadyExistsException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleUserAlreadyExists(ex: UserAlreadyExistsException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse("User already exists")
//     }
//
//     @ExceptionHandler(UserNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleUserNotFound(ex: UserNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("User")
//     }
//
//     @ExceptionHandler(ReviewRequestNotFoundException::class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     fun handleReviewRequestNotFound(ex: ReviewRequestNotFoundException, request: WebRequest): ApiErrorResponse {
//         return notFoundResponse("Review request")
//     }
//
//     @ExceptionHandler(ContentIsNotEditableException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleContentIsNotEditable(ex: ContentIsNotEditableException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(UnauthorizedException::class)
//     @ResponseStatus(HttpStatus.UNAUTHORIZED)
//     fun handleIllegalAccess(ex: UnauthorizedException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(BadRequestException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleBadRequest(ex: BadRequestException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(DuplicateReviewRequestException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleDuplicateReviewRequest(ex: DuplicateReviewRequestException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(DuplicateReviewException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleDuplicateReview(ex: DuplicateReviewException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(SearchUnitTypeNotSupportedException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun handleSearchUnitTypeNotSupported(
//         ex: SearchUnitTypeNotSupportedException,
//         request: WebRequest
//     ): ApiErrorResponse {
//         return ApiErrorResponse("Search unit with type ${ex.unsupportedType} not supported, [ARTICLE, CONTENT] only available.")
//     }
//
//     @ExceptionHandler(ReviewResponseAlreadyExistsException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun reviewResponseAlreadyExists(ex: ReviewResponseAlreadyExistsException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     @ExceptionHandler(ContentAlreadyExistsException::class)
//     @ResponseStatus(HttpStatus.BAD_REQUEST)
//     fun reviewContentAlreadyExists(ex: ContentAlreadyExistsException, request: WebRequest): ApiErrorResponse {
//         return ApiErrorResponse(ex.message)
//     }
//
//     private fun notFoundResponse(entity: String): ApiErrorResponse {
//         return ApiErrorResponse("$entity not found")
//     }
// }
