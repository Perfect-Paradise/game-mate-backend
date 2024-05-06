package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import com.perfectparadise.gamemate.model.response.UserInfoResponse
import com.perfectparadise.gamemate.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("/user/info")
    fun getUserInfo(): UserInfoResponse {
        val platformAuthentication = PlatformAuthentication.getFromSecurityContext()
        return UserInfoResponse.from(platformAuthentication.platformUser)
    }

    @PatchMapping("/user/info")
    fun partialUpdateUserInfo(@RequestBody request: UpdateUserInfoRequest): UserInfoResponse {
        return userService.partialUpdateUserInfo(request)
    }

}