package com.perfectparadise.gamemate.model.request

data class UpdateUserInfoRequest(
    val displayName: String?,
    // TODO: update email requires verification process
    val avatarUrl: String?,
    val description: String?,
)
