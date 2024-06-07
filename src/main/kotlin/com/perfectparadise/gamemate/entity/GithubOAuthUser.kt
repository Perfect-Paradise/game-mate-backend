package com.perfectparadise.gamemate.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class GithubOAuthUser(

    @Id
    val id: Long,

    val name: String?,

    val email: String?,

    val avatarUrl: String?,

    @OneToOne
    val platformUser: PlatformUser
)