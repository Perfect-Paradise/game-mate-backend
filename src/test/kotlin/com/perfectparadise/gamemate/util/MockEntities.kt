package com.perfectparadise.gamemate.util

import com.perfectparadise.gamemate.entity.GithubOAuthUser
import com.perfectparadise.gamemate.entity.PlatformUser

fun mockPlatformUser(): PlatformUser {
    return PlatformUser(1L, "test", "test", "test", "test")
}

fun mockGithubOAuthUser(): GithubOAuthUser {
    return mockGithubOAuthUser(mockPlatformUser())
}

fun mockGithubOAuthUser(platformUser: PlatformUser): GithubOAuthUser {
    return GithubOAuthUser(1L, "test", "test", "test", platformUser)
}