package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.user.service.UserDetailsServiceImpl
import java.io.IOException

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter



class JWTAuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    private val tokenProvider: JWTProvider? = null

    @Autowired
    private val userDetailsService: UserDetailsServiceImpl? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {

            val jwt = getJwt(request)
            if (jwt != null && tokenProvider!!.validateJwtToken(jwt)) {
                val username = tokenProvider.getUserNameFromJwtToken(jwt)

                val userDetails = userDetailsService!!.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Can NOT set user authentication -> Message: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.replace("Bearer ", "")
        } else null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JWTAuthTokenFilter::class.java)
    }
}