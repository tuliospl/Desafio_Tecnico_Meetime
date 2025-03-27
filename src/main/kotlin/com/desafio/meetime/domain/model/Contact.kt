package com.desafio.meetime.domain.model

import com.desafio.meetime.infrastructure.adapter.dto.request.ContactRequest
import com.desafio.meetime.infrastructure.adapter.dto.request.Properties
import com.desafio.meetime.infrastructure.adapter.dto.response.ContactResponse

data class Contact(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
) {
    init {
        require(firstName.isNotBlank()) { "First name cannot be blank" }
        require(lastName.isNotBlank()) { "Last name cannot be blank" }
        require(email.isNotBlank() && email.contains("@")) { "Invalid email address" }
    }

    companion object {
        fun fromHubspot(response: ContactResponse): Contact {
            return Contact(
                id = response.id,
                firstName = response.properties.firstname,
                lastName = response.properties.lastname,
                email = response.properties.email,
            )
        }
    }

    fun toHubspotRequest(): ContactRequest = ContactRequest(
        properties = Properties(
            firstname = firstName,
            lastname = lastName,
            email = email,
        ),
    )
}
