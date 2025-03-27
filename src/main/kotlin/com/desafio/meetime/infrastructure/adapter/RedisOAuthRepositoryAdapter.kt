package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.OAuthToken
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import com.desafio.meetime.infrastructure.exception.GetTokenException
import com.desafio.meetime.infrastructure.exception.SaveTokenException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

class RedisOAuthRepositoryAdapter(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper,
) : OAuthRepositoryPort {

    private val logger = LoggerFactory.getLogger(RedisOAuthRepositoryAdapter::class.java)
    private val TOKEN_KEY = "hubspot:oauth_token"

    override fun saveToken(token: OAuthToken) {
        try {
            val tokenJson = objectMapper.writeValueAsString(token)
            redisTemplate.opsForValue().set(
                TOKEN_KEY,
                tokenJson,
                token.expiresIn.toLong(),
                TimeUnit.SECONDS,
            )
        } catch (e: Exception) {
            logger.error("Error saving token: ${e.message}", e)
            throw SaveTokenException(e.message, e.cause)
        }
    }

    override fun getStoredToken(): OAuthToken? {
        try {
            val tokenJson = redisTemplate.opsForValue().get(TOKEN_KEY)
            return tokenJson?.let {
                objectMapper.readValue(it, OAuthToken::class.java)
            }
        } catch (e: Exception) {
            logger.error("Error getting token: ${e.message}", e)
            throw GetTokenException(e.message, e.cause)
        }
    }
}
