package com.desafio.meetime.domain.port.input

import com.desafio.meetime.domain.model.WebhookContactCreated

interface WebhookUseCase {
    fun contactCreation(webhookContactCreated: WebhookContactCreated)
}
