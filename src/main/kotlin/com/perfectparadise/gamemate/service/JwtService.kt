package com.perfectparadise.gamemate.service

import com.perfectparadise.gamemate.entity.PlatformUser
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service

@Service
class JwtService {

    private val key = Jwts.SIG.HS256.key().build()

    fun generateToken(platformUser: PlatformUser): String {
        return Jwts.builder()
            .subject(platformUser.id.toString())
            .signWith(key)
            .compact()
    }

}