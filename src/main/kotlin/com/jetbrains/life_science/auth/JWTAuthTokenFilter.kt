package com.jetbrains.life_science.auth

import com.jetbrains.life_science.auth.jwt.JWTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTAuthTokenFilter(
    private val jwtService: JWTService,
    @Qualifier("handlerExceptionResolver")
    val resolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    val bearer = "Bearer"

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            tryAuth(request, filterChain, response)
        } catch (exception: Exception) {
            resolver.resolveException(request, response, null, exception) ?: throw IllegalStateException(exception)
        }
    }

    private fun tryAuth(
        request: HttpServletRequest,
        filterChain: FilterChain,
        response: HttpServletResponse
    ) {
        val jwt = getJwt(request)
        if (jwt != null) {
            jwtService.validateJwtToken(jwt)
            auth(jwt, request)
        }
        filterChain.doFilter(request, response)
    }

    private fun auth(jwt: String, request: HttpServletRequest) {
        val username = jwtService.getUserNameFromJwtToken(jwt)

        val userDetails = userDetailsService.loadUserByUsername(username)
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (authHeader != null && authHeader.startsWith(bearer)) {
            authHeader.substring(bearer.length).trim()
        } else null
    }
}
