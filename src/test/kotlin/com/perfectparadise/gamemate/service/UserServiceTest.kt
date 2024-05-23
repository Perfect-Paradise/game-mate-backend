package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import com.perfectparadise.gamemate.repository.PlatformUserRepository
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

        every { platformUserRepository.save(any()) } answers { firstArg() }

        // act
        val result = userService.createPlatformUser(displayName)

        // assert
        assertEquals(displayName, result.displayName)
        assertEquals("I am a good person.", result.description)
    }

    @Test
    fun `should partial update user info`() {
        // arrange
        val request = UpdateUserInfoRequest(
            displayName = "new name",
            description = "new description"
        )
        val platformUser = PlatformUser(
            displayName = "old name",
            description = "old description"
        )

        val mockPlatformAuthentication = PlatformAuthentication(platformUser, true)
        mockkObject(PlatformAuthentication.Companion)
        every { PlatformAuthentication.getFromSecurityContext() } returns mockPlatformAuthentication
        every { platformUserRepository.save(any()) } answers { firstArg() }

        // act
        val result = userService.partialUpdateUserInfo(request)

        // assert
        assertEquals(request.displayName, result.displayName)
        assertEquals(request.description, result.description)
    }
}