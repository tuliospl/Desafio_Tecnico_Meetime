package com.desafio.meetime.domain.model

import com.desafio.meetime.fixed.fixedContact
import com.desafio.meetime.fixed.fixedContactResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ContactTest {

    @Test
    fun `should create Contact with valid data`() {
        val id = "1"
        val firstName = "Test"
        val lastName = "Test"
        val email = "test@test.com"

        val contact = Contact(id, firstName, lastName, email)

        assertEquals(id, contact.id)
        assertEquals(firstName, contact.firstName)
        assertEquals(lastName, contact.lastName)
        assertEquals(email, contact.email)
    }

    @Test
    fun `should throw exception for blank firstName`() {
        val exception = assertThrows<RuntimeException> {
            Contact(null, "", "Test", "test@test.com")
        }
        assertEquals("First name cannot be blank", exception.message)
    }

    @Test
    fun `should throw exception for blank lastName`() {
        val exception = assertThrows<RuntimeException> {
            Contact(null, "Test", "", "test@test.com")
        }
        assertEquals("Last name cannot be blank", exception.message)
    }

    @Test
    fun `should throw exception for invalid format email`() {
        val exception = assertThrows<RuntimeException> {
            Contact(null, "Test", "Test", "testtest.com")
        }
        assertEquals("Invalid email address", exception.message)
    }

    @Test
    fun `should throw exception for email is blank`() {
        val exception = assertThrows<RuntimeException> {
            Contact(null, "Test", "Test", "")
        }
        assertEquals("Invalid email address", exception.message)
    }

    @Test
    fun `should create Contact from ContactResponse`() {
        val response = fixedContactResponse()
        val contact = Contact.fromHubspot(response)

        assertEquals(response.id, contact.id)
        assertEquals(response.properties.firstname, contact.firstName)
        assertEquals(response.properties.lastname, contact.lastName)
        assertEquals(response.properties.email, contact.email)
    }

    @Test
    fun `should convert Contact to ContactRequest`() {
        val contact = fixedContact()

        val request = contact.toHubspotRequest()

        assertEquals(contact.firstName, request.properties.firstname)
        assertEquals(contact.lastName, request.properties.lastname)
        assertEquals(contact.email, request.properties.email)
    }
}
