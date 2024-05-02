package com.perfectparadise.gamemate.repository

import com.perfectparadise.gamemate.entity.PlatformUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlatformUserRepository : JpaRepository<PlatformUser, Long> {
}