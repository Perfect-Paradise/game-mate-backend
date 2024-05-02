package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.response.UserInfoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
) {

    @GetMapping("/user/info")
    fun getUserInfo(): UserInfoResponse {
        val platformAuthentication = PlatformAuthentication.getFromSecurityContext()
        return UserInfoResponse.from(platformAuthentication.platformUser)
    }

}