package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
@Suppress("unused")
class JwtServiceTest {

    @InjectMockKs(injectImmutable = true)
    private lateinit var jwtService: JwtService

    private val secret = "testtesttesttesttesttesttesttest"

    @MockK
    private lateinit var platformUserRepository: PlatformUserRepository

    @Test
    fun `should generate token`() {
        // arrange
        val platformUser = PlatformUser(1L, "test", "test")

        // act
        val result = jwtService.generateToken(platformUser)

        // assert
        assertNotNull(result)
    }

    @Test
    fun `should parse token`() {
        // arrange
        val platformUser = PlatformUser(1L, "test", "test")
        val token = jwtService.generateToken(platformUser)

        every { platformUserRepository.findById(any()) } returns Optional.of(platformUser)

        // act
        val result = jwtService.parseToken(token)

        // assert
        assertNotNull(result)
    }
}