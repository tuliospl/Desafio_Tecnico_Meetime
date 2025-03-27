package com.desafio.meetime.fixed

import com.desafio.meetime.domain.model.WebhookContactCreated

fun fixedWebhookContactCreated() = WebhookContactCreated(
    id = 1L,
    createdAt = System.currentTimeMillis(),
    createdBy = 123L,
    eventType = "CREATE",
    propertyName = "contact",
    active = true,
)
