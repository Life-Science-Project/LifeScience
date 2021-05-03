package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.util.getLogger
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
        response.writer.println("HTTP Status 401 - " + e.message)
    }

    companion object {
        private val logger = getLogger()
    }
}
