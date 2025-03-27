package com.desafio.meetime.application.service

import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import com.desafio.meetime.fixed.fixedOAuthToken
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OAuthApplicationServiceTest {

    private val authenticationPort: OAuthAuthenticationPort = mockk()
    private val repositoryPort: OAuthRepositoryPort = mockk()
    private val oAuthApplicationService = OAuthApplicationService(authenticationPort, repositoryPort)

    @Test
    fun `generateAuthorizationUrl should return authorization URL`() {
        val expectedUrl = "http://example.com/auth"

        every { oAuthApplicationService.generateAuthorizationUrl() } returns expectedUrl
        every { authenticationPort.generateAuthorizationUrl() } returns expectedUrl

        authenticationPort.generateAuthorizationUrl()

        verify { oAuthApplicationService.generateAuthorizationUrl() }
        verify { authenticationPort.generateAuthorizationUrl() }
    }

    @Test
    fun `authenticate should return OAuthToken and save it`() {
        val code = "authCode"
        val token = fixedOAuthToken()

        every { authenticationPort.getAccessToken(any()) } returns token
        every { repositoryPort.saveToken(any()) } returns Unit

        oAuthApplicationService.authenticate(code)

        assertEquals(token, oAuthApplicationService.authenticate(code))
        verify { repositoryPort.saveToken(token) }
    }

    @Test
    fun `refreshToken should return new OAuthToken and save it`() {
        val refreshToken = "refreshToken"
        val newToken = fixedOAuthToken()

        every { authenticationPort.refreshAccessToken(any()) } returns newToken
        every { repositoryPort.saveToken(any()) } returns Unit

        oAuthApplicationService.refreshToken(refreshToken)

        verify(exactly = 1) { authenticationPort.refreshAccessToken(refreshToken) }
        verify(exactly = 1) { repositoryPort.saveToken(newToken) }
    }
}
