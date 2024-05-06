package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.repository.PlatformUserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,
    private val platformUserRepository: PlatformUserRepository,
) {

    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(platformUser: PlatformUser): String {
        return Jwts.builder()
            .subject(platformUser.id.toString())
            .signWith(key)
            .compact()
    }

    fun parseToken(token: String): PlatformAuthentication {
        val userId: String
        try {
            userId = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid token")
        }

        val platformUser = platformUserRepository.findById(userId.toLong())
            .orElseThrow { IllegalArgumentException("Invalid token") }

        return PlatformAuthentication(platformUser, true)
    }

}