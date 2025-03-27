package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.infrastructure.interceptor.HubSpotAuthInterceptor
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HubSpotClientConfig(
    private val hubspotAuthInterceptor: HubSpotAuthInterceptor,
) {

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return hubspotAuthInterceptor
    }
}
