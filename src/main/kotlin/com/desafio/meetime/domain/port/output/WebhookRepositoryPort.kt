package com.desafio.meetime.domain.port.output

import com.desafio.meetime.domain.model.WebhookContactCreated

interface WebhookRepositoryPort {
    fun saveWebhookContact(webhookContactCreated: WebhookContactCreated)
}
