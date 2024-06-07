package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.model.request.LoginRequest
import com.perfectparadise.gamemate.model.request.SignupRequest
import com.perfectparadise.gamemate.model.response.TokenResponse
import com.perfectparadise.gamemate.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/auth/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): TokenResponse {
        return authService.signup(signupRequest)
    }

    @PostMapping("/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): TokenResponse {
        return authService.login(loginRequest)
    }
}