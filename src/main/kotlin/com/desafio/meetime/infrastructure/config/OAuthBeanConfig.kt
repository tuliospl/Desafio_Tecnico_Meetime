package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.application.service.OAuthApplicationService
import com.desafio.meetime.domain.port.input.OAuthUseCase
import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import com.desafio.meetime.infrastructure.adapter.HubSpotAuthClient
import com.desafio.meetime.infrastructure.adapter.HubspotOAuthAdapter
import com.desafio.meetime.infrastructure.adapter.RedisOAuthRepositoryAdapter
import com.desafio.meetime.infrastructure.properties.HubSpotProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class OAuthBeanConfig(
    private val hubSpotAuthClient: HubSpotAuthClient,
    private val hubSpotProperties: HubSpotProperties,
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun oAuthAuthenticationPort(): OAuthAuthenticationPort {
        return HubspotOAuthAdapter(hubSpotAuthClient, hubSpotProperties)
    }

    @Bean
    fun oAuthRepositoryPort(): OAuthRepositoryPort {
        return RedisOAuthRepositoryAdapter(redisTemplate, objectMapper)
    }

    @Bean
    fun oAuthUseCase(
        authenticationPort: OAuthAuthenticationPort,
        repositoryPort: OAuthRepositoryPort,
    ): OAuthUseCase {
        return OAuthApplicationService(authenticationPort, repositoryPort)
    }
}
