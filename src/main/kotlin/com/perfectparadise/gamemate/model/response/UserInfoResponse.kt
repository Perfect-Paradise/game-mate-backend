package com.perfectparadise.gamemate.model.response

import com.perfectparadise.gamemate.entity.PlatformUser

data class UserInfoResponse(
    val id: Long,

    val displayName: String,

    val description: String,
) {
    companion object {
        fun from(platformUser: PlatformUser): UserInfoResponse = UserInfoResponse(
            id = platformUser.id,
            displayName = platformUser.displayName,
            description = platformUser.description,
        )
    }
}
