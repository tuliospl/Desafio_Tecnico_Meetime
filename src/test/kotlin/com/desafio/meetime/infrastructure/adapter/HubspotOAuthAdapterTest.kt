package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.fixed.fixedOAuthToken
import com.desafio.meetime.infrastructure.exception.AuthenticationException
import com.desafio.meetime.infrastructure.exception.RefreshTokenException
import com.desafio.meetime.infrastructure.properties.HubSpotProperties
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HubspotOAuthAdapterTest {

    private val hubSpotAuthClient: HubSpotAuthClient = mockk()
    private val hubSpotProperties: HubSpotProperties = mockk()
    private val hubspotOAuthAdapter = HubspotOAuthAdapter(hubSpotAuthClient, hubSpotProperties)

    @Test
    fun `generateAuthorizationUrl should return correct URL`() {
        every { hubSpotProperties.clientId } returns "clientId"
        every { hubSpotProperties.redirectUri } returns "http://localhost"
        every { hubSpotProperties.scopes } returns "scope1 scope2"

        val expectedUrl = "https://app.hubspot.com/oauth/authorize" +
            "?client_id=clientId" +
            "&redirect_uri=http://localhost" +
            "&scope=scope1 scope2"

        val url = hubspotOAuthAdapter.generateAuthorizationUrl()

        assertEquals(expectedUrl, url)
    }

    @Test
    fun `getAccessToken should return OAuthToken when client call is successful`() {
        val code = "authCode"
        val response = fixedOAuthToken()

        every { hubSpotAuthClient.getAccessToken(any(), any(), any(), any(), any()) } returns response
        every { hubSpotProperties.clientId } returns "clientId"
        every { hubSpotProperties.clientSecret } returns "clientSecret"
        every { hubSpotProperties.redirectUri } returns "http://localhost"

        val token = hubspotOAuthAdapter.getAccessToken(code)

        assertEquals("accessToken", token.accessToken)
        assertEquals("refreshToken", token.refreshToken)
        assertEquals(0, token.expiresIn)
        assertEquals("Bearer", token.tokenType)
        assertEquals(true, token.expired)
        verify(exactly = 1) { hubSpotAuthClient.getAccessToken(any(), any(), any(), any(), any()) }
    }

    @Test
    fun `getAccessToken should throw AuthenticationException when client call fails`() {
        val code = "authCode"
        val exception = RuntimeException("Client error")

        every { hubSpotAuthClient.getAccessToken(any(), any(), any(), any(), any()) } throws exception
        every { hubSpotProperties.clientId } returns "clientId"
        every { hubSpotProperties.clientSecret } returns "clientSecret"
        every { hubSpotProperties.redirectUri } returns "http://localhost"

        assertThrows<AuthenticationException> {
            hubspotOAuthAdapter.getAccessToken(code)
        }
    }

    @Test
    fun `refreshAccessToken should return OAuthToken when client call is successful`() {
        val refreshToken = "refreshToken"
        val response = fixedOAuthToken()

        every { hubSpotAuthClient.getAccessToken(any(), any(), any(), any(), any()) } returns response
        every { hubSpotProperties.clientId } returns "clientId"
        every { hubSpotProperties.clientSecret } returns "clientSecret"
        every { hubSpotProperties.redirectUri } returns "http://localhost"

        val token = hubspotOAuthAdapter.refreshAccessToken(refreshToken)

        assertEquals("accessToken", token.accessToken)
        assertEquals("refreshToken", token.refreshToken)
        assertEquals(0, token.expiresIn)
        assertEquals("Bearer", token.tokenType)
        assertEquals(true, token.expired)
        verify(exactly = 1) { hubSpotAuthClient.getAccessToken(any(), any(), any(), any(), any()) }
    }

    @Test
    fun `refreshAccessToken should throw RefreshTokenException when client call fails`() {
        val refreshToken = "refreshToken"

        every { hubSpotAuthClient.getAccessToken(
            any(), any(), any(), any(), any(),
        ) } throws RuntimeException("Client error")
        every { hubSpotProperties.clientId } returns "clientId"
        every { hubSpotProperties.clientSecret } returns "clientSecret"
        every { hubSpotProperties.redirectUri } returns "http://localhost"

        assertThrows<RefreshTokenException> {
            hubspotOAuthAdapter.refreshAccessToken(refreshToken)
        }
    }
}
