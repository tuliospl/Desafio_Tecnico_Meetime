package com.desafio.meetime.infrastructure.adapter.dto.request

data class ContactRequest(
    val properties: Properties,
)

data class Properties(
    val firstname: String,
    val lastname: String,
    val email: String,
)
