package com.jetbrains.life_science.exceptions

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.LinkedHashMap
import java.time.LocalDateTime

@ControllerAdvice
class ControllerAdvisor : ResponseEntityExceptionHandler() {

    @ExceptionHandler(SectionNotFoundException::class)
    fun handleSectionNotFound(ex: SectionNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleNotFound("Section")
    }

    @ExceptionHandler(MethodNotFoundException::class)
    fun handleMethodNotFound(ex: MethodNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleNotFound("Method")
    }

    @ExceptionHandler(ArticleNotFoundException::class)
    fun handleArticleNotFound(ex: ArticleNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleNotFound("Article")
    }

    private fun handleNotFound(entity: String): ResponseEntity<Any> {
        return errorResponse(HttpStatus.NOT_FOUND, "$entity not found")
    }

    private fun errorResponse(responseStatus: HttpStatus, message: String): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = LocalDateTime.now()
        body["message"] = message
        return ResponseEntity(body, responseStatus)
    }
}
