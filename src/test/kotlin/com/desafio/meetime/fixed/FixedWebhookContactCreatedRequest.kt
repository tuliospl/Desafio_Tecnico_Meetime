package com.desafio.meetime.fixed

import com.desafio.meetime.interfaces.dto.WebhookContactCreatedRequest

fun fixedWebhookContactCreatedRequest() = WebhookContactCreatedRequest(
    id = 1,
    createdAt = 3600,
    createdBy = 3600,
    eventType = "CONTACT_CREATED",
    propertyName = "test",
    active = true,
)
