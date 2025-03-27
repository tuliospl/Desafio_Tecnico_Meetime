package com.desafio.meetime.domain.port.input

import com.desafio.meetime.domain.model.Contact

interface CreateContactUseCase {
    fun createContact(contact: Contact): Contact
}
