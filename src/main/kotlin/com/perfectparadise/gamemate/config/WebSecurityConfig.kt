package com.perfectparadise.gamemate.config

import com.perfectparadise.gamemate.filter.JwtTokenFilter
import com.perfectparadise.gamemate.handler.OAuth2LoginSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val jwtTokenFilter: JwtTokenFilter,
) {

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/index.html", "/oauth2/callback.html", "/webjars/**").permitAll()
                    .requestMatchers("/hello/**").permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling { e ->
                e.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            }
            .oauth2Login {
                it
                    .loginPage("/login")
                    .defaultSuccessUrl("/index.html")
                    .successHandler(oAuth2LoginSuccessHandler)
            }

        return http.build()
    }
}