package com.perfectparadise.gamemate.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class GithubOAuthUser(

    @Id
    val id: Long,

    val name: String,

    @OneToOne
    val platformUser: PlatformUser
)