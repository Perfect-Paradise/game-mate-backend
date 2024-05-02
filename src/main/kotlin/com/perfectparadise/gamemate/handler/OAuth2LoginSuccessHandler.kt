package com.perfectparadise.gamemate.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.perfectparadise.gamemate.model.response.LoginResponse
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

        val loginResponse = LoginResponse(token)

        // Set content type to JSON
        response.contentType = "application/json"
        // Write the token to the response body
        response.writer.write(ObjectMapper().writeValueAsString(loginResponse))
    }
}