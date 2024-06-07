package com.perfectparadise.gamemate.model.request

data class SignupRequest(
    val email: String,
    val password: String,
    val displayName: String,
    val avatarUrl: String,
    val description: String,
)