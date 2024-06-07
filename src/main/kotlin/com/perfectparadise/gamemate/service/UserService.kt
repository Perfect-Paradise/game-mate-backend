package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import com.perfectparadise.gamemate.model.response.UserInfoResponse
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val platformUserRepository: PlatformUserRepository
) {

    fun createPlatformUser(displayName: String, email: String?, avatarUrl: String): PlatformUser {
        return platformUserRepository.save(
            PlatformUser(
                displayName = displayName,
                email = email,
                avatarUrl = avatarUrl,
                description = "I am a good person."
            )
        )
    }

    fun partialUpdateUserInfo(request: UpdateUserInfoRequest): UserInfoResponse {
        val platformUser = PlatformAuthentication.getFromSecurityContext().platformUser
        platformUser.partialUpdate(request)
        platformUserRepository.save(platformUser)

        return UserInfoResponse.from(platformUser)
    }
}