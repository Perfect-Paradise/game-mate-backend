package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.GithubOAuthUser
import com.perfectparadise.gamemate.repository.GithubOAuthUserRepository
import org.springframework.stereotype.Service

@Service
class GithubOAuthUserService(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val githubOAuthUserRepository: GithubOAuthUserRepository,
) {

    fun login(githubOAuth2User: Map<String, Any>): String {
        val id = (githubOAuth2User["id"] as Int).toLong()
        val name = githubOAuth2User["name"] as String
        val email = githubOAuth2User["email"] as String?
        val avatarUrl = githubOAuth2User["avatar_url"] as String

        val githubOAuthUser = githubOAuthUserRepository.findById(id)
            .orElseGet { createGithubOAuthUser(id, name, email, avatarUrl) }

        return jwtService.generateToken(githubOAuthUser.platformUser)
    }

    private fun createGithubOAuthUser(id: Long, name: String, email: String?, avatarUrl: String): GithubOAuthUser {
        val platformUser = userService.createPlatformUser(name, email, avatarUrl)

        return githubOAuthUserRepository.save(
            GithubOAuthUser(
                id = id,
                name = name,
                email = email,
                avatarUrl = avatarUrl,
                platformUser = platformUser
            )
        )
    }
}