package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.WebhookContactCreated
import com.desafio.meetime.domain.port.output.WebhookRepositoryPort
import com.desafio.meetime.infrastructure.exception.WebhookContactCreationSaveException
import org.slf4j.LoggerFactory

class HubSpotWebhookContactCreationAdapter(
    private val webhookContactCreationEventRepository: WebhookContactCreationEventRepository,
) : WebhookRepositoryPort {

    private val logger = LoggerFactory.getLogger(HubSpotWebhookContactCreationAdapter::class.java)

    override fun saveWebhookContact(webhookContactCreated: WebhookContactCreated) {
        try {
            webhookContactCreationEventRepository.save(webhookContactCreated)
        } catch (e: Exception) {
            logger.error("Error saving webhook contact: ${e.message}", e)
            throw WebhookContactCreationSaveException(e.message, e.cause)
        }
    }
}
