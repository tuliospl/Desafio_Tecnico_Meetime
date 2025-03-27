package com.desafio.meetime.infrastructure.interceptor

import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import com.desafio.meetime.fixed.fixedOAuthToken
import com.desafio.meetime.infrastructure.exception.GetTokenException
import com.desafio.meetime.infrastructure.exception.RefreshTokenException
import feign.RequestTemplate
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HubSpotAuthInterceptorTest {

    private val oAuthRepositoryPort: OAuthRepositoryPort = mockk()
    private val oAuthAuthenticationPort: OAuthAuthenticationPort = mockk()
    private val hubSpotAuthInterceptor = HubSpotAuthInterceptor(oAuthRepositoryPort, oAuthAuthenticationPort)

    @Test
    fun `apply should set Authorization header with current token`() {
        val token = fixedOAuthToken()
        val template = RequestTemplate()

        every { oAuthRepositoryPort.getStoredToken() } returns token

        hubSpotAuthInterceptor.apply(template)

        assertEquals("Bearer accessToken", template.headers()["Authorization"]?.first())
        verify(exactly = 1) { oAuthRepositoryPort.getStoredToken() }
    }

    @Test
    fun `apply should refresh token and set Authorization header when token is expired`() {
        val expiredToken = fixedOAuthToken().copy(expired = true)
        val newToken = fixedOAuthToken().copy(accessToken = "newAccessToken", refreshToken = "newRefreshToken")
        val template = RequestTemplate()

        every { oAuthRepositoryPort.getStoredToken() } returns expiredToken
        every { oAuthAuthenticationPort.refreshAccessToken(expiredToken.refreshToken) } returns newToken
        every { oAuthRepositoryPort.saveToken(newToken) } returns Unit

        hubSpotAuthInterceptor.apply(template)

        assertEquals("Bearer newAccessToken", template.headers()["Authorization"]?.first())
        verify(exactly = 1) { oAuthRepositoryPort.getStoredToken() }
        verify(exactly = 1) { oAuthAuthenticationPort.refreshAccessToken(expiredToken.refreshToken) }
        verify(exactly = 1) { oAuthRepositoryPort.saveToken(newToken) }
    }

    @Test
    fun `apply should throw GetTokenException when no token is found in repository`() {
        val template = RequestTemplate()
        every { oAuthRepositoryPort.getStoredToken() } returns null

        assertThrows<GetTokenException> {
            hubSpotAuthInterceptor.apply(template)
        }
    }

    @Test
    fun `apply should throw GetTokenException when getting token fails`() {
        every { oAuthRepositoryPort.getStoredToken() } throws Exception("Get token error")
        val template = RequestTemplate()

        assertThrows<RefreshTokenException> {
            hubSpotAuthInterceptor.apply(template)
        }
    }
}
