package com.desafio.meetime.application.service

import com.desafio.meetime.domain.model.WebhookContactCreated
import com.desafio.meetime.domain.port.input.WebhookUseCase
import com.desafio.meetime.domain.port.output.WebhookRepositoryPort
import org.springframework.stereotype.Service

@Service
class WebhookService(
    private val webhookRepositoryPort: WebhookRepositoryPort,
) : WebhookUseCase {
    override fun contactCreation(webhookContactCreated: WebhookContactCreated) {
        return webhookRepositoryPort.saveWebhookContact(webhookContactCreated)
    }
}
