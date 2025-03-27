package com.desafio.meetime.domain.model

import com.desafio.meetime.fixed.fixedOAuthToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OAuthTokenTest {

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun `isExpired should return true when token is expired`() {
        val token = fixedOAuthToken().copy(expired = true)

        val result = token.isExpired()

        assertTrue(result)
    }

    @Test
    fun `isExpired should return false when token is not expired`() {
        val token = fixedOAuthToken().copy(expiresIn = 1000)

        val result = token.isExpired()

        assertFalse(result)
    }

    @Test
    fun `should deserialize JSON to OAuthToken`() {
        val json = """
            {
                "access_token": "accessTokenValue",
                "refresh_token": "refreshTokenValue",
                "expires_in": 3600,
                "token_type": "Bearer",
                "is_expired": false
            }
        """.trimIndent()

        val token: OAuthToken = objectMapper.readValue(json)

        assertEquals("accessTokenValue", token.accessToken)
        assertEquals("refreshTokenValue", token.refreshToken)
        assertEquals(3600, token.expiresIn)
        assertEquals("Bearer", token.tokenType)
        assertEquals(false, token.expired)
    }
}
