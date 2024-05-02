package com.perfectparadise.gamemate.controller

import com.ninjasquad.springmockk.MockkBean
import com.perfectparadise.gamemate.service.HelloService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(MockKExtension::class)
@WebMvcTest(HelloController::class)
class HelloControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var helloService: HelloService

    @Test
    fun helloTest() {
        every { helloService.helloToYou("World") } returns "Hello, World!"

        mockMvc.perform(get("/hello?name=World"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, World!"))
    }
}