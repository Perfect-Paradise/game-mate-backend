package com.perfectparadise.gamemate.handler

import com.perfectparadise.gamemate.service.GithubOAuthUserService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority

@ExtendWith(MockKExtension::class)
class OAuth2LoginSuccessHandlerTest {

    @InjectMockKs
    private lateinit var oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler

    @MockK
    private lateinit var githubOAuthUserService: GithubOAuthUserService

    @Test
    fun `onAuthenticationSuccess should call login and sendRedirect with correct arguments`() {
        // Arrange
        val request: HttpServletRequest = mockk()
        val response: HttpServletResponse = mockk(relaxed = true)
        val authentication = mockk<OAuth2AuthenticationToken>()
        val attributes = mapOf("id" to 1, "name" to "test")
        val authorities = listOf(OAuth2UserAuthority(attributes))
        val principal = DefaultOAuth2User(authorities, attributes, "name")

        every { authentication.principal } returns principal
        every { authentication.authorizedClientRegistrationId } returns "github"
        every { githubOAuthUserService.login(any()) } returns "testToken"

        // Act
        oAuth2LoginSuccessHandler.onAuthenticationSuccess(request, response, authentication)

        // Assert
        verify { githubOAuthUserService.login(attributes) }
        verify { response.sendRedirect("/oauth2/callback.html?token=testToken") }
    }
}