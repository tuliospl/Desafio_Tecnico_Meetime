package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.model.WebhookContactCreated
import com.desafio.meetime.domain.port.input.WebhookUseCase
import com.desafio.meetime.interfaces.dto.WebhookContactCreatedRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webhook")
class WebhookController(
    private val webhookUseCase: WebhookUseCase,
) {
    @PostMapping("/contact-created")
    fun contactCreation(@RequestBody webhookContactCreatedRequest: List<WebhookContactCreatedRequest>) {
        webhookContactCreatedRequest.forEach { webhookContactCreatedRequest ->
            val webhookContactCreated = WebhookContactCreated(
                id = webhookContactCreatedRequest.id,
                createdAt = webhookContactCreatedRequest.createdAt,
                createdBy = webhookContactCreatedRequest.createdBy,
                eventType = webhookContactCreatedRequest.eventType,
                propertyName = webhookContactCreatedRequest.propertyName,
                active = webhookContactCreatedRequest.active,
            )
            webhookUseCase.contactCreation(webhookContactCreated)
        }
    }
}
