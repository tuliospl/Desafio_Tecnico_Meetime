package com.desafio.meetime.interfaces.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WebhookContactCreatedRequest(
    val id: Long,
    val createdAt: Long,
    val createdBy: Long,
    val eventType: String?,
    val propertyName: String?,
    val active: Boolean,
)
