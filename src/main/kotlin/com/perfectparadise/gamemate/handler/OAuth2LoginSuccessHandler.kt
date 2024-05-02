package com.perfectparadise.gamemate.handler

import com.perfectparadise.gamemate.service.GithubOAuthUserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val githubOAuthUserService: GithubOAuthUserService,
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken

        val token = when (val registrationId = oAuth2AuthenticationToken.authorizedClientRegistrationId) {
            "github" -> githubOAuthUserService.login(oAuth2AuthenticationToken.principal.attributes)
            else -> throw IllegalArgumentException("Unsupported registrationId: $registrationId")
        }

        // Redirect to a page on your domain with the token in the URL
        response.sendRedirect("/oauth2/callback.html?token=$token")
    }
}