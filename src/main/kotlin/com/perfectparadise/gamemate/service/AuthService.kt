package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.EmailPasswordUser
import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.model.request.LoginRequest
import com.perfectparadise.gamemate.model.request.SignupRequest
import com.perfectparadise.gamemate.model.response.TokenResponse
import com.perfectparadise.gamemate.repository.EmailPasswordUserRepository
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val emailPasswordUserRepository: EmailPasswordUserRepository,
    private val platformUserRepository: PlatformUserRepository,
    private val jwtService: JwtService,
) {
    companion object {
        val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    fun signup(signupRequest: SignupRequest): TokenResponse {
        if (emailPasswordUserRepository.findByEmail(signupRequest.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }

        val platformUser = createPlatformUser(signupRequest)
        val emailPasswordUser = EmailPasswordUser(
            email = signupRequest.email,
            password = encodePassword(signupRequest.password),
            platformUser = platformUser,
        )
        emailPasswordUserRepository.save(emailPasswordUser)

        return TokenResponse(
            token = jwtService.generateToken(platformUser)
        )
    }

    fun createPlatformUser(signupRequest: SignupRequest): PlatformUser {
        return platformUserRepository.save(
            PlatformUser(
                email = signupRequest.email,
                displayName = signupRequest.displayName,
                avatarUrl = signupRequest.avatarUrl,
                description = signupRequest.description,
            )
        )
    }

    fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun login(loginRequest: LoginRequest): TokenResponse {
        val emailPasswordUser = emailPasswordUserRepository.findByEmail(loginRequest.email)
            ?: throw IllegalArgumentException("Invalid email or password")

        if (!passwordEncoder.matches(loginRequest.password, emailPasswordUser.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }

        return TokenResponse(
            token = jwtService.generateToken(emailPasswordUser.platformUser)
        )
    }
}