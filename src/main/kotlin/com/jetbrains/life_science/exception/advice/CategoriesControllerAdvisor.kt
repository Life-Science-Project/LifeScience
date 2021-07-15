package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.exception.category.CategoryNoParentsException
import com.jetbrains.life_science.exception.category.CategoryNotFoundException
import com.jetbrains.life_science.exception.category.CategoryParentNotFoundException
import com.jetbrains.life_science.exception.handler.ApiExceptionView
import com.jetbrains.life_science.exception.maker.makeExceptionView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CategoriesControllerAdvisor {
    @ExceptionHandler(CategoryNoParentsException::class)
    fun handleCategoryNoParentsException(exception: CategoryNoParentsException): ResponseEntity<ApiExceptionView> {
        return ResponseEntity(
            makeExceptionView(400_002),
            HttpStatus.BAD_REQUEST
        )
    }

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
}
