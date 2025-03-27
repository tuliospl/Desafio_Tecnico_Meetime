package com.desafio.meetime.domain.port.output

import com.desafio.meetime.domain.model.OAuthToken

interface OAuthRepositoryPort {
    fun saveToken(token: OAuthToken)
    fun getStoredToken(): OAuthToken?
}
