package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.fixed.fixedWebhookContactCreated
import com.desafio.meetime.infrastructure.exception.WebhookContactCreationSaveException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HubSpotWebhookContactCreationAdapterTest {

    private val webhookContactCreationEventRepository: WebhookContactCreationEventRepository = mockk()
    private val hubSpotWebhookContactCreationAdapter =
        HubSpotWebhookContactCreationAdapter(webhookContactCreationEventRepository)

    @Test
    fun `saveWebhookContact should save WebhookContactCreated`() {
        val webhookContactCreated = fixedWebhookContactCreated()

        every { webhookContactCreationEventRepository.save(webhookContactCreated) } returns webhookContactCreated

        hubSpotWebhookContactCreationAdapter.saveWebhookContact(webhookContactCreated)

        verify(exactly = 1) { webhookContactCreationEventRepository.save(webhookContactCreated) }
    }

    @Test
    fun `saveWebhookContact should throw WebhookContactCreationSaveException when save fails`() {
        val webhookContactCreated = fixedWebhookContactCreated()

        every { webhookContactCreationEventRepository.save(
            webhookContactCreated,
        ) } throws RuntimeException("Save error")

        assertThrows<WebhookContactCreationSaveException> {
            hubSpotWebhookContactCreationAdapter.saveWebhookContact(webhookContactCreated)
        }
    }
}
