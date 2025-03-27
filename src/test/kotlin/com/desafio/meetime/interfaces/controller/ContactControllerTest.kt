package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.port.input.CreateContactUseCase
import com.desafio.meetime.fixed.fixedContact
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class)
class ContactControllerTest {
    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var createContactUseCase: CreateContactUseCase

    private lateinit var contactController: ContactController

    @BeforeEach
    fun setup() {
        contactController = ContactController(createContactUseCase)
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build()
    }

    @Test
    fun `should create contact successfully`() {
        val contact = fixedContact()

        every { createContactUseCase.createContact(any()) } returns contact

        mockMvc.perform(
            post("/contact/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstname", "Test")
                .param("lastname", "Test")
                .param("email", "test@test.com"),
        )
            .andExpect(status().isCreated)
    }
}
