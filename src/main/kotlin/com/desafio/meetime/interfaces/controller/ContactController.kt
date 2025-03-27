package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.model.Contact
import com.desafio.meetime.domain.port.input.CreateContactUseCase
import com.desafio.meetime.interfaces.dto.ContactRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contact")
class ContactController(
    private val createContactUseCase: CreateContactUseCase,
) {
    @PostMapping("/create", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createContact(@ModelAttribute contactRequest: ContactRequest): ResponseEntity<Contact> {
        val contact = Contact(
            firstName = contactRequest.firstname,
            lastName = contactRequest.lastname,
            email = contactRequest.email,
        )
        val createdContact = createContactUseCase.createContact(contact)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact)
    }
}
