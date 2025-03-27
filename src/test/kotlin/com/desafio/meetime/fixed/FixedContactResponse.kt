package com.desafio.meetime.fixed

import com.desafio.meetime.infrastructure.adapter.dto.response.ContactResponse
import com.desafio.meetime.infrastructure.adapter.dto.response.Properties

fun fixedContactResponse() = ContactResponse(
    id = "1",
    properties = fixedProperties(),
)

fun fixedProperties() = Properties(
    firstname = "Test",
    lastname = "Test",
    email = "test@test.com",
)
