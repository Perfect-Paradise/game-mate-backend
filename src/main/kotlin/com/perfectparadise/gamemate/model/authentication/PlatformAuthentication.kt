package com.perfectparadise.gamemate.model.authentication

import com.perfectparadise.gamemate.entity.PlatformUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

fun anonymousAuthentication(): PlatformAuthentication {
    return PlatformAuthentication(
        PlatformUser(0, "Anonymous User", ""),
        false
    )
}

data class PlatformAuthentication(
    val platformUser: PlatformUser,
    val authenticated: Boolean,
) : Authentication {

    companion object {
        fun getFromSecurityContext(): PlatformAuthentication {
            return SecurityContextHolder.getContext().authentication as PlatformAuthentication
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun getName(): String {
        return platformUser.id.toString()
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return platformUser
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun getDetails(): Any {
        return platformUser
    }

}