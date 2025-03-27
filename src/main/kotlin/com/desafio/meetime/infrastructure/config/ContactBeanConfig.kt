package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.application.service.ContactApplicationService
import com.desafio.meetime.domain.port.input.CreateContactUseCase
import com.desafio.meetime.domain.port.output.ContactRepositoryPort
import com.desafio.meetime.infrastructure.adapter.HubSpotContactClient
import com.desafio.meetime.infrastructure.adapter.HubspotContactAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ContactBeanConfig(
    private val hubSpotContactClient: HubSpotContactClient,
) {

    @Bean
    fun contactRepositoryPort(): ContactRepositoryPort {
        return HubspotContactAdapter(hubSpotContactClient)
    }

    @Bean
    fun createContactUseCase(contactRepositoryPort: ContactRepositoryPort): CreateContactUseCase {
        return ContactApplicationService(contactRepositoryPort)
    }
}
