package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.fixed.fixedOAuthToken
import com.desafio.meetime.infrastructure.exception.GetTokenException
import com.desafio.meetime.infrastructure.exception.SaveTokenException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

class RedisOAuthRepositoryAdapterTest {

    private val redisTemplate: StringRedisTemplate = mockk()
    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    private val redisOAuthRepositoryAdapter = RedisOAuthRepositoryAdapter(redisTemplate, objectMapper)

    @Test
    fun `saveToken should save OAuthToken to Redis`() {
        // Arrange
        val token = fixedOAuthToken()
        val tokenJson = objectMapper.writeValueAsString(token)
        every { redisTemplate.opsForValue().set(any(), any(), any(), any()) } returns Unit

        // Act
        redisOAuthRepositoryAdapter.saveToken(token)

        // Assert
        verify(exactly = 1) {
            redisTemplate.opsForValue().set(
                "hubspot:oauth_token",
                tokenJson,
                0,
                TimeUnit.SECONDS,
            )
        }
    }

    @Test
    fun `saveToken should throw SaveTokenException when Redis operation fails`() {
        val token = fixedOAuthToken()
        val exception = RuntimeException("Redis error")
        every { redisTemplate.opsForValue().set(any(), any(), any(), any()) } throws exception

        assertThrows<SaveTokenException> {
            redisOAuthRepositoryAdapter.saveToken(token)
        }
    }

    @Test
    fun `getStoredToken should return OAuthToken from Redis`() {
        val token = fixedOAuthToken()
        val tokenJson = objectMapper.writeValueAsString(token)
        every { redisTemplate.opsForValue().get("hubspot:oauth_token") } returns tokenJson

        val result = redisOAuthRepositoryAdapter.getStoredToken()

        assertEquals(token.accessToken, result?.accessToken)
        assertEquals(token.refreshToken, result?.refreshToken)
        assertEquals(token.expiresIn, result?.expiresIn)
        assertEquals(token.tokenType, result?.tokenType)
        assertEquals(token.expired, result?.expired)
        verify(exactly = 1) { redisTemplate.opsForValue().get("hubspot:oauth_token") }
    }

    @Test
    fun `getStoredToken should return null when no token is stored in Redis`() {
        every { redisTemplate.opsForValue().get("hubspot:oauth_token") } returns null

        val result = redisOAuthRepositoryAdapter.getStoredToken()

        assertNull(result)
        verify(exactly = 1) { redisTemplate.opsForValue().get("hubspot:oauth_token") }
    }

    @Test
    fun `getStoredToken should throw GetTokenException when Redis operation fails`() {
        val exception = RuntimeException("Redis error")
        every { redisTemplate.opsForValue().get("hubspot:oauth_token") } throws exception

        assertThrows<GetTokenException> {
            redisOAuthRepositoryAdapter.getStoredToken()
        }
    }
}
