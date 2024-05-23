package com.perfectparadise.gamemate.model.response

data class ErrorResponse(
    val errorType: ErrorType,
    val message: String,
)

enum class ErrorType {
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,
}