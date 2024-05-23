package com.perfectparadise.gamemate.controller

import com.ninjasquad.springmockk.MockkBean
import com.perfectparadise.gamemate.service.HelloService
import com.perfectparadise.gamemate.service.JwtService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(
    HelloController::class,
    excludeAutoConfiguration = [SecurityAutoConfiguration::class, OAuth2ClientAutoConfiguration::class],
)
class HelloControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var helloService: HelloService

    @MockkBean
    @Suppress("unused")
    private lateinit var jwtService: JwtService

    @Test
    fun helloTest() {
        every { helloService.helloToYou("World") } returns "Hello, World!"

        mockMvc.perform(get("/hello?name=World"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, World!"))
    }

    @Test
    fun helloNotFoundTest() {
        mockMvc.perform(get("/hello/not-found"))
            .andExpect(status().isNotFound)
    }
}