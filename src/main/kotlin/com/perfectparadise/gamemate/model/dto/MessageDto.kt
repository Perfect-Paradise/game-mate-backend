package com.perfectparadise.gamemate.model.dto

import java.time.Instant

data class MessageDto(
    val message: String,
    val time: Instant,
)
