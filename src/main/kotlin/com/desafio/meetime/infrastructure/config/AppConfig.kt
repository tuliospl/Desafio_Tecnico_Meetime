package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.infrastructure.properties.HubSpotProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(HubSpotProperties::class)
class AppConfig
