package com.desafio.meetime.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthToken(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("is_expired")
    val expired: Boolean,
) {
    fun isExpired(): Boolean {
        return System.currentTimeMillis() >= calculateExpirationTime()
    }

    private fun calculateExpirationTime(): Long {
        return System.currentTimeMillis() + (expiresIn * TRANSFORMER_MILLIS)
    }

    companion object {
        const val TRANSFORMER_MILLIS = 1000
    }
}
