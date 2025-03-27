package com.desafio.meetime.infrastructure.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val code: Int,
    val message: String,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
)
