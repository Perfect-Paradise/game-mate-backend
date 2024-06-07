package com.perfectparadise.gamemate.repository

import com.perfectparadise.gamemate.entity.EmailPasswordUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailPasswordUserRepository : JpaRepository<EmailPasswordUser, Long> {
    fun findByEmail(email: String): EmailPasswordUser?
}