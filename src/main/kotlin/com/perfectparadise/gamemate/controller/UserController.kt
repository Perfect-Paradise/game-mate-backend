package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.model.response.OAuth2UserResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal user: OAuth2User): OAuth2UserResponse {
        return OAuth2UserResponse(
            id = user.getAttribute<Int>("id").toString(),
            name = user.getAttribute("name"),
            avatarUrl = user.getAttribute("avatar_url")
        )
    }

}