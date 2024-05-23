package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.exception.NotFoundException
import com.perfectparadise.gamemate.model.response.ErrorResponse
import com.perfectparadise.gamemate.model.response.ErrorType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error { "something went wrong $e" }
        return ResponseEntity(
            ErrorResponse(
                errorType = ErrorType.INTERNAL_SERVER_ERROR,
                message = e.message ?: "Internal server error"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                errorType = ErrorType.NOT_FOUND,
                message = e.message ?: "Not found"
            ),
            HttpStatus.NOT_FOUND
        )
    }
}