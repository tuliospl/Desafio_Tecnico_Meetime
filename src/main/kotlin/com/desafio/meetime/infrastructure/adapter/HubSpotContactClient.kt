package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.infrastructure.adapter.dto.request.ContactRequest
import com.desafio.meetime.infrastructure.adapter.dto.response.ContactResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "hubSpotContactClient", url = "https://api.hubapi.com")
interface HubSpotContactClient {

    @PostMapping("/crm/v3/objects/contacts")
    fun createContact(@RequestBody request: ContactRequest): ResponseEntity<ContactResponse>
}
