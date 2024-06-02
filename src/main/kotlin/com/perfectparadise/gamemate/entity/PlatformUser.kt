package com.perfectparadise.gamemate.entity

import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class PlatformUser(

    @Id
    @GeneratedValue
    val id: Long = 0, // 0 is a dummy value

    var displayName: String,

    var email: String? = null,

    var avatarUrl: String,

    var description: String,
) {

    fun partialUpdate(request: UpdateUserInfoRequest) {
        request.displayName?.let { displayName = it }
        request.avatarUrl?.let { avatarUrl = it }
        request.description?.let { description = it }
    }
}
