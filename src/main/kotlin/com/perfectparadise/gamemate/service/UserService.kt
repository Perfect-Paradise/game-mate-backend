package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val platformUserRepository: PlatformUserRepository
) {

    fun createPlatformUser(displayName: String): PlatformUser {
        return platformUserRepository.save(
            PlatformUser(
                displayName = displayName,
                description = "I am a good person."
            )
        )
    }
}