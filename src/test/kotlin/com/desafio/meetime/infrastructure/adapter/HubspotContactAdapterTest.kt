package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.fixed.fixedContact
import com.desafio.meetime.fixed.fixedContactResponse
import com.desafio.meetime.infrastructure.adapter.dto.response.ContactResponse
import com.desafio.meetime.infrastructure.exception.ContactSaveException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.ResponseEntity

class HubspotContactAdapterTest {

    private val hubSpotContactClient: HubSpotContactClient = mockk()
    private val hubspotContactAdapter = HubspotContactAdapter(hubSpotContactClient)

    @Test
    fun `save should return Contact when client call is successful`() {
        val contact = fixedContact()
        val contactResponse = fixedContactResponse()
        val responseEntity: ResponseEntity<ContactResponse> = ResponseEntity.ok(contactResponse)

        every { hubSpotContactClient.createContact(contact.toHubspotRequest()) } returns responseEntity

        val result = hubspotContactAdapter.save(contact)

        assertEquals(contactResponse.id, result.id)
        assertEquals(contact.firstName, result.firstName)
        assertEquals(contact.lastName, result.lastName)
        assertEquals(contact.email, result.email)
        verify(exactly = 1) { hubSpotContactClient.createContact(contact.toHubspotRequest()) }
    }

    @Test
    fun `save should throw ContactSaveException when client call fails`() {
        val contact = fixedContact()

        every { hubSpotContactClient.createContact(any()) } throws RuntimeException("Client error")

        assertThrows<ContactSaveException> {
            hubspotContactAdapter.save(contact)
        }
    }
}
