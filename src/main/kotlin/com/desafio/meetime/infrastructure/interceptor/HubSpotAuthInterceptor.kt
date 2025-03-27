package com.desafio.meetime.infrastructure.interceptor

import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import com.desafio.meetime.infrastructure.exception.GetTokenException
import com.desafio.meetime.infrastructure.exception.RefreshTokenException
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component

@Component
class HubSpotAuthInterceptor(
    private val oAuthRepositoryPort: OAuthRepositoryPort,
    private val oAuthAuthenticationPort: OAuthAuthenticationPort,
) : RequestInterceptor {

    override fun apply(template: RequestTemplate) {
        try {
            val token = oAuthRepositoryPort.getStoredToken()
                ?: throw IllegalStateException("No token found in repository")

            val currentToken = if (token.expired) {
                val newToken = oAuthAuthenticationPort.refreshAccessToken(token.refreshToken)
                oAuthRepositoryPort.saveToken(newToken)
                newToken
            } else {
                token
            }

            template.header("Authorization", "Bearer ${currentToken.accessToken}")
        } catch (e: IllegalStateException) {
            throw GetTokenException("Could not authenticate request due to token expired error", e)
        } catch (e: Exception) {
            throw RefreshTokenException("Could not authenticate request due to token refresh error", e)
        }
    }
}
