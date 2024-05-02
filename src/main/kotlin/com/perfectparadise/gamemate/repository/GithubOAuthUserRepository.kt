package com.perfectparadise.gamemate.repository

import com.perfectparadise.gamemate.entity.GithubOAuthUser
import org.springframework.data.jpa.repository.JpaRepository

interface GithubOAuthUserRepository : JpaRepository<GithubOAuthUser, Long> {
}