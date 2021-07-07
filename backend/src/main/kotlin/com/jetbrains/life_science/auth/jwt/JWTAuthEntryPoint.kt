package com.jetbrains.life_science.auth.jwt

import com.jetbrains.life_science.util.getLogger
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JWTAuthEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        e: AuthenticationException
    ) {
        logger.error("Unauthorized error. Message - {}", e.message)
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.println("{ message: ${e.message} }")
    }

    companion object {
        private val logger = getLogger()
    }
}
