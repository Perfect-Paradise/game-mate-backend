package com.perfectparadise.gamemate

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest // for integration tests
@AutoConfigureMockMvc
class GameMateBackendIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should response unauthorized if no jwt token present`() {
        mockMvc.perform(get("/"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should through IllegalArgumentException when token is invalid`() {
        assertThrows<IllegalArgumentException> {
            mockMvc.perform(
                get("/")
                    .header("Authorization", "Bearer invalid-token")
            )
        }
    }

}
