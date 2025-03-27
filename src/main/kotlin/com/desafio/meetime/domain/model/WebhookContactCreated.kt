package com.desafio.meetime.domain.model

data class WebhookContactCreated(
    val id: Long,
    val createdAt: Long,
    val createdBy: Long,
    val eventType: String?,
    val propertyName: String?,
    val active: Boolean,
)
