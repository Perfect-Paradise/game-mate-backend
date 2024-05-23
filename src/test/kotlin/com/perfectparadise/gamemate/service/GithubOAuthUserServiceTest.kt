package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.GithubOAuthUser
import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.repository.GithubOAuthUserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GithubOAuthUserServiceTest {

    @InjectMockKs
    private lateinit var githubOAuthUserService: GithubOAuthUserService

    @MockK
    @Suppress("unused")
    private lateinit var userService: UserService

    @MockK
    private lateinit var jwtService: JwtService

    @MockK
    private lateinit var githubOAuthUserRepository: GithubOAuthUserRepository

    @Test
    fun `login should call generateToken with correct argument`() {
        // arrange
        val githubOAuth2User = mapOf("id" to 1, "name" to "test")
        val expectedGithubOAuthUser = GithubOAuthUser(
            1L, "test", PlatformUser(1L, "test", "test")
        )

        every { githubOAuthUserRepository.findById(any()) } returns Optional.of(expectedGithubOAuthUser)
        every { jwtService.generateToken(any()) } returns "testToken"

        // act
        val result = githubOAuthUserService.login(githubOAuth2User)

        // assert
        verify { jwtService.generateToken(expectedGithubOAuthUser.platformUser) }
        assertEquals("testToken", result)
    }
}