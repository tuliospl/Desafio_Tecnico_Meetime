package com.desafio.meetime.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "hubspot")
@Component
data class HubSpotProperties(
    var clientId: String = "",
    var clientSecret: String = "",
    var redirectUri: String = "",
    var scopes: String = "",
)
