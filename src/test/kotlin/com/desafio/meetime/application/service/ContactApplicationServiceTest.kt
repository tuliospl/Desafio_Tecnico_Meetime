package com.desafio.meetime.application.service

import com.desafio.meetime.domain.port.output.ContactRepositoryPort
import com.desafio.meetime.fixed.fixedContact
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ContactApplicationServiceTest {

    private val contactRepositoryPort: ContactRepositoryPort = mockk()
    private val contactApplicationService = ContactApplicationService(contactRepositoryPort)

    @Test
    fun `createContact should save and return the contact`() {
        val contact = fixedContact()

        every { contactRepositoryPort.save(any()) } returns contact

        contactApplicationService.createContact(contact)

        verify { contactRepositoryPort.save(contact) }
    }
}
