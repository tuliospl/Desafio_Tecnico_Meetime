package com.desafio.meetime.domain.port.output

import com.desafio.meetime.domain.model.OAuthToken

interface OAuthAuthenticationPort {
    fun generateAuthorizationUrl(): String
    fun getAccessToken(code: String): OAuthToken
    fun refreshAccessToken(refreshToken: String): OAuthToken
}
