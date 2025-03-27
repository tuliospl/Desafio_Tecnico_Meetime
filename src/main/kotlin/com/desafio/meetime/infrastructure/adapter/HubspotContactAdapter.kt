package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.Contact
import com.desafio.meetime.domain.port.output.ContactRepositoryPort
import com.desafio.meetime.infrastructure.exception.ContactSaveException
import org.slf4j.LoggerFactory

class HubspotContactAdapter(
    private val hubSpotContactClient: HubSpotContactClient,
) : ContactRepositoryPort {

    private val logger = LoggerFactory.getLogger(HubspotContactAdapter::class.java)

    override fun save(contact: Contact): Contact {
        try {
            val response = hubSpotContactClient.createContact(contact.toHubspotRequest())
            return Contact.fromHubspot(response.body!!)
        } catch (e: Exception) {
            logger.error("Error saving contact: ${e.message}", e)
            throw ContactSaveException(e.message, e.cause)
        }
    }
}
