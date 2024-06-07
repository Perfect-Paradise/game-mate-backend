package com.perfectparadise.gamemate.entity

import com.perfectparadise.gamemate.model.request.UpdateUserInfoRequest
import jakarta.persistence.*

@Entity
class PlatformUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var displayName: String,

    var email: String? = null,

    var avatarUrl: String?,

    var description: String?,
) {

    fun partialUpdate(request: UpdateUserInfoRequest) {
        request.displayName?.let { displayName = it }
        request.avatarUrl?.let { avatarUrl = it }
        request.description?.let { description = it }
    }
}
