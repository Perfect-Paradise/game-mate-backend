package com.perfectparadise.gamemate.filter

import com.perfectparadise.gamemate.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // skip if the request is authenticated
        if (SecurityContextHolder.getContext().authentication != null) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwtToken = authHeader.substring(7)

            val platformAuthentication = jwtService.parseToken(jwtToken)

            val securityContext = SecurityContextHolder.createEmptyContext()
            securityContext.authentication = platformAuthentication
            SecurityContextHolder.setContext(securityContext)
        }

        filterChain.doFilter(request, response)
    }
}