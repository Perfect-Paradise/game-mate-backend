package com.perfectparadise.gamemate.model.response

import com.perfectparadise.gamemate.entity.PlatformUser

data class UserInfoResponse(
    val id: Long,

    val displayName: String,

    val email: String?,

    val avatarUrl: String,

    val description: String,
) {
    companion object {
        fun from(platformUser: PlatformUser): UserInfoResponse = UserInfoResponse(
            id = platformUser.id,
            displayName = platformUser.displayName,
            email = platformUser.email,
            avatarUrl = platformUser.avatarUrl,
            description = platformUser.description,
        )
    }
}
