package com.desafio.meetime.infrastructure.adapter.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactResponse(
    val id: String,
    val properties: Properties,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Properties(
    val firstname: String,
    val lastname: String,
    val email: String,
)
