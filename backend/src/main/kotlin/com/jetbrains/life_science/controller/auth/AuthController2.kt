package com.jetbrains.life_science.controller.auth

import com.jetbrains.life_science.auth.AuthRequest
import com.jetbrains.life_science.auth.AuthResponseView
import com.jetbrains.life_science.auth2.refresh.RefreshTokenCode
import com.jetbrains.life_science.auth2.service.AuthRequestToCredentialsAdapter
import com.jetbrains.life_science.auth2.service.AuthService
import com.jetbrains.life_science.controller.auth.view.AccessTokenView
import com.jetbrains.life_science.controller.auth.view.AccessTokenViewMapper
import com.jetbrains.life_science.user.master.dto.NewUserDTO
import com.jetbrains.life_science.user.master.dto.NewUserDTOToInfoAdapter
import com.jetbrains.life_science.user.master.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/auth2")
class AuthController2(
        val authService: AuthService,
        val accessTokenViewMapper: AccessTokenViewMapper,
        val userService: UserService
) {

    @Operation(summary = "Sign in")
    @PostMapping
    fun login(@Validated @RequestBody authRequest: AuthRequest,
              httpServletRequest: HttpServletResponse
    ): AccessTokenView {
        val credentials = AuthRequestToCredentialsAdapter(authRequest)
        val (accessToken, refreshToken) = authService.login(credentials)
        setRefreshTokenToCookie(httpServletRequest, refreshToken)
        return accessTokenViewMapper.toView(accessToken)
    }

    @Operation(summary = "Sign up")
    @PostMapping
    fun register(@Validated @RequestBody userDto: NewUserDTO): AccessTokenView {
        val user = userService.createUser(NewUserDTOToInfoAdapter(userDto))

    }

    @Operation(summary = "Refreshes JWT and Refresh tokens")
    @PostMapping("/refresh")
    fun refreshToken() {

    }

    @PatchMapping
    fun logout() {
        TODO()
    }

    private fun setRefreshTokenToCookie(httpServletRequest: HttpServletResponse, refreshTokenCode: RefreshTokenCode) {
        val cookie = Cookie("refresh", refreshTokenCode.code).apply {
            isHttpOnly = true
        }
        httpServletRequest.addCookie(cookie)
    }

}
