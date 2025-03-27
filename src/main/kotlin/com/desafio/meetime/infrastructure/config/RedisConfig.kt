package com.desafio.meetime.infrastructure.config

import com.desafio.meetime.domain.model.OAuthToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, OAuthToken> {
        val template = RedisTemplate<String, OAuthToken>()
        template.setConnectionFactory(redisConnectionFactory)

        val jackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer()

        template.valueSerializer = jackson2JsonRedisSerializer
        template.keySerializer = StringRedisSerializer()
        template.hashValueSerializer = jackson2JsonRedisSerializer

        return template
    }
}
