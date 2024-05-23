package com.perfectparadise.gamemate.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HelloServiceTest {

    @InjectMockKs
    private lateinit var helloService: HelloService

    @Test
    fun `should return hello message`() {
        // arrange
        val name = "test"

        // act
        val result = helloService.helloToYou(name)

        // assert
        assertEquals("Hello, test!", result)
    }
}