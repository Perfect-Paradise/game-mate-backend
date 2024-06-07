package com.perfectparadise.gamemate.entity

import jakarta.persistence.*

@Entity
class EmailPasswordUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @OneToOne
    val platformUser: PlatformUser
)