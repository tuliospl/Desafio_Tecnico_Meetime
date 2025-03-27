package com.desafio.meetime.application.service

import com.desafio.meetime.domain.model.Contact
import com.desafio.meetime.domain.port.input.CreateContactUseCase
import com.desafio.meetime.domain.port.output.ContactRepositoryPort
import org.springframework.stereotype.Service

@Service
class ContactApplicationService(
    private val contactRepositoryPort: ContactRepositoryPort,
) : CreateContactUseCase {
    override fun createContact(contact: Contact): Contact {
        return contactRepositoryPort.save(contact)
    }
}
