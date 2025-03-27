package com.desafio.meetime.application.service

import com.desafio.meetime.domain.port.output.WebhookRepositoryPort
import com.desafio.meetime.fixed.fixedWebhookContactCreated
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebhookServiceTest {

    private val webhookRepositoryPort: WebhookRepositoryPort = mockk()
    private val webhookService = WebhookService(webhookRepositoryPort)

    @Test
    fun `contactCreation should save WebhookContactCreated`() {
        val webhookContactCreated = fixedWebhookContactCreated()

        every { webhookRepositoryPort.saveWebhookContact(any()) } returns Unit

        webhookService.contactCreation(webhookContactCreated)

        verify(exactly = 1) { webhookRepositoryPort.saveWebhookContact(webhookContactCreated) }
    }
}
