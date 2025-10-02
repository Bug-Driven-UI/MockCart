package com.example.cart.cart.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<String> {
        logger.error(ex.message, ex)

        return ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}