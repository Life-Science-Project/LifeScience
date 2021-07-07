package com.jetbrains.life_science.controller.auth

import com.jetbrains.life_science.controller.auth.view.AuthRequestDTO
import com.jetbrains.life_science.auth.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.auth.service.AuthRequestToCredentialsAdapter
import com.jetbrains.life_science.auth.service.AuthService
import com.jetbrains.life_science.controller.auth.view.AccessTokenView
import com.jetbrains.life_science.controller.auth.view.AccessTokenViewMapper
import com.jetbrains.life_science.exception.auth.RefreshTokenNotFoundException
import com.jetbrains.life_science.user.credentials.dto.NewUserDTO
import com.jetbrains.life_science.user.credentials.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.util.email
import io.swagger.v3.oas.annotations.Operation
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authService: AuthService,
    val accessTokenViewMapper: AccessTokenViewMapper,
    val credentialsService: CredentialsService
) {

    @Operation(summary = "Sign in")
    @PostMapping("/signin")
    fun login(
        @Validated @RequestBody authRequestDTO: AuthRequestDTO,
        httpServletResponse: HttpServletResponse
    ): AccessTokenView {
        val credentials = AuthRequestToCredentialsAdapter(authRequestDTO)
        val (accessToken, refreshToken) = authService.login(credentials)
        setRefreshTokenToCookie(httpServletResponse, refreshToken)
        return accessTokenViewMapper.toView(accessToken)
    }

    @Operation(summary = "Sign up")
    @PostMapping("/register")
    @Transactional
    fun register(
        @Validated @RequestBody userDto: NewUserDTO,
        response: HttpServletResponse
    ): AccessTokenView {
        val credentials = credentialsService.createUser(NewUserDTOToInfoAdapter(userDto))
        val (accessToken, refreshToken) = authService.register(credentials)
        setRefreshTokenToCookie(response, refreshToken)
        return accessTokenViewMapper.toView(accessToken)
    }

    @Operation(summary = "Refreshes JWT and Refresh tokens")
    @PostMapping("/refresh")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
        principal: Principal
    ): AccessTokenView {
        val userCredentials = credentialsService.getByEmail(principal.email)
        val refreshTokenCode = getRefreshToken(request.cookies)
        val (accessToken, refreshToken) = authService.refreshTokens(userCredentials, refreshTokenCode)
        setRefreshTokenToCookie(response, refreshToken)
        return accessTokenViewMapper.toView(accessToken)
    }

    private fun getRefreshToken(cookies: Array<Cookie>): RefreshTokenCode {
        val cookie = cookies.find { it.name == "refresh" } ?: throw RefreshTokenNotFoundException()
        return RefreshTokenCode(cookie.value)
    }

    private fun setRefreshTokenToCookie(httpServletResponse: HttpServletResponse, refreshTokenCode: RefreshTokenCode) {
        val cookie = Cookie("refresh", refreshTokenCode.code).apply {
            isHttpOnly = true
        }
        httpServletResponse.addCookie(cookie)
    }
}
