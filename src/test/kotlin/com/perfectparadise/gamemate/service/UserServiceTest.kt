package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import com.perfectparadise.gamemate.util.mockPlatformUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @InjectMockKs
    private lateinit var userService: UserService

    @MockK
    private lateinit var platformUserRepository: PlatformUserRepository

    @Test
    fun `should create platform user`() {
        // arrange
        val displayName = "test"
        val email = "test@test.com"
        val avatarUrl = "test"

        every { platformUserRepository.save(any()) } answers { firstArg() }

        // act
        val result = userService.createPlatformUser(displayName, email, avatarUrl)

        // assert
        assertEquals(displayName, result.displayName)
        assertEquals(email, result.email)
        assertEquals(avatarUrl, result.avatarUrl)
        assertEquals("I am a good person.", result.description)
    }

    @Test
    fun `should partial update user info`() {
        // arrange
        val request = UpdateUserInfoRequest(
            displayName = "new name",
            avatarUrl = "new url",
            description = "new description"
        )
        val platformUser = mockPlatformUser()

        val mockPlatformAuthentication = PlatformAuthentication(platformUser, true)
        mockkObject(PlatformAuthentication.Companion)
        every { PlatformAuthentication.getFromSecurityContext() } returns mockPlatformAuthentication
        every { platformUserRepository.save(any()) } answers { firstArg() }

        // act
        val result = userService.partialUpdateUserInfo(request)

        // assert
        assertEquals(request.displayName, result.displayName)
        assertEquals(request.avatarUrl, result.avatarUrl)
        assertEquals(request.description, result.description)
    }
}