package com.perfectparadise.gamemate.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class PlatformUser(

    @Id
    @GeneratedValue
    val id: Long = 0, // 0 is a dummy value

    val displayName: String,

    val description: String,
)
