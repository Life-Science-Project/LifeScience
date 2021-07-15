package com.jetbrains.life_science.auth

import com.jetbrains.life_science.exception.auth.ForbiddenOperationException
import com.jetbrains.life_science.util.getLogger
import org.springframework.beans.factory.annotation.Qualifier
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JWTAuthEntryPoint(
    @Qualifier("handlerExceptionResolver")
    val resolver: HandlerExceptionResolver
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        logger.trace("AuthenticationException: {}", exception.message)
        resolver.resolveException(request, response, null, ForbiddenOperationException())
    }

    companion object {
        private val logger = getLogger()
    }
}
