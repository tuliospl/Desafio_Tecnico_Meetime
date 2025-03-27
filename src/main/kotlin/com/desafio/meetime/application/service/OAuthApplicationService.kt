package com.desafio.meetime.application.service

import com.desafio.meetime.domain.model.OAuthToken
import com.desafio.meetime.domain.port.input.OAuthUseCase
import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.domain.port.output.OAuthRepositoryPort
import org.springframework.stereotype.Service

@Service
class OAuthApplicationService(
    private val authenticationPort: OAuthAuthenticationPort,
    private val repositoryPort: OAuthRepositoryPort,
) : OAuthUseCase {

    override fun generateAuthorizationUrl(): String {
        return authenticationPort.generateAuthorizationUrl()
    }

    override fun authenticate(code: String): OAuthToken {
        val token = authenticationPort.getAccessToken(code)
        repositoryPort.saveToken(token)
        return token
    }

    override fun refreshToken(refreshToken: String): OAuthToken {
        val newToken = authenticationPort.refreshAccessToken(refreshToken)
        repositoryPort.saveToken(newToken)
        return newToken
    }
}
