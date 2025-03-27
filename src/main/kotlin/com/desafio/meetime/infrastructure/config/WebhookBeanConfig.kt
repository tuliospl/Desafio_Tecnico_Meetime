package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.application.service.WebhookService
import com.desafio.meetime.domain.port.input.WebhookUseCase
import com.desafio.meetime.domain.port.output.WebhookRepositoryPort
import com.desafio.meetime.infrastructure.adapter.HubSpotWebhookContactCreationAdapter
import com.desafio.meetime.infrastructure.adapter.WebhookContactCreationEventRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebhookBeanConfig {

    @Bean
    fun webhookRepositoryPort(
        webhookContactCreationEventRepository: WebhookContactCreationEventRepository,
    ): WebhookRepositoryPort {
        return HubSpotWebhookContactCreationAdapter(webhookContactCreationEventRepository)
    }

    @Bean
    fun webhookUseCase(webhookRepositoryPort: WebhookRepositoryPort): WebhookUseCase {
        return WebhookService(webhookRepositoryPort)
    }
}
