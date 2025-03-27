package com.desafio.meetime.domain.port.input

import com.desafio.meetime.domain.model.OAuthToken

interface OAuthUseCase {
    fun generateAuthorizationUrl(): String
    fun authenticate(code: String): OAuthToken
    fun refreshToken(refreshToken: String): OAuthToken
}
