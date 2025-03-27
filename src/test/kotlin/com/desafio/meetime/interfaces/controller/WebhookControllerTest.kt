package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.port.input.WebhookUseCase
import com.desafio.meetime.fixed.fixedWebhookContactCreatedRequest
import com.desafio.meetime.interfaces.dto.WebhookContactCreatedRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class)
class WebhookControllerTest {
    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var webhookUseCase: WebhookUseCase

    private lateinit var webhookController: WebhookController

    @BeforeEach
    fun setup() {
        webhookController = WebhookController(webhookUseCase)
        mockMvc = MockMvcBuilders.standaloneSetup(webhookController).build()
    }

    @Test
    fun `should process single webhook contact creation request`() {
        val webhookRequest = listOf(fixedWebhookContactCreatedRequest())

        every { webhookUseCase.contactCreation(any()) } returns Unit

        mockMvc.perform(
            post("/webhook/contact-created")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(webhookRequest)),
        )
            .andExpect(status().isOk)

        verify(exactly = 1) { webhookUseCase.contactCreation(
            withArg {
                assert(it.id == 1L)
                assert(it.eventType == "CONTACT_CREATED")
                assert(it.active)
            },
        ) }
    }

    @Test
    fun `should process multiple webhook contact creation requests`() {
        val webhookRequests = listOf(fixedWebhookContactCreatedRequest(), fixedWebhookContactCreatedRequest())

        every { webhookUseCase.contactCreation(any()) } returns Unit

        mockMvc.perform(
            post("/webhook/contact-created")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(webhookRequests)),
        )
            .andExpect(status().isOk)

        verify(exactly = 2) { webhookUseCase.contactCreation(any()) }
    }

    @Test
    fun `should handle empty list of webhook requests`() {
        val emptyWebhookRequests = emptyList<WebhookContactCreatedRequest>()

        every { webhookUseCase.contactCreation(any()) } returns Unit

        mockMvc.perform(
            post("/webhook/contact-created")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyWebhookRequests)),
        )
            .andExpect(status().isOk)

        verify(exactly = 0) { webhookUseCase.contactCreation(any()) }
    }

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }
}
