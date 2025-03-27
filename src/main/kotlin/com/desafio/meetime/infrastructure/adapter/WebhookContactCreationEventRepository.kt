package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.WebhookContactCreated
import org.springframework.data.mongodb.repository.MongoRepository

interface WebhookContactCreationEventRepository : MongoRepository<WebhookContactCreated, String>
