package com.desafio.meetime.domain.port.output

import com.desafio.meetime.domain.model.Contact

interface ContactRepositoryPort {
    fun save(contact: Contact): Contact
}
