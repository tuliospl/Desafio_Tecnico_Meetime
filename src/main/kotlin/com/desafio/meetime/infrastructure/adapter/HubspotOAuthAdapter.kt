package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.OAuthToken
import com.desafio.meetime.domain.port.output.OAuthAuthenticationPort
import com.desafio.meetime.infrastructure.exception.AuthenticationException
import com.desafio.meetime.infrastructure.exception.RefreshTokenException
import com.desafio.meetime.infrastructure.properties.HubSpotProperties
import org.slf4j.LoggerFactory

class HubspotOAuthAdapter(
    private val hubSpotAuthClient: HubSpotAuthClient,
    private val hubSpotProperties: HubSpotProperties,
) : OAuthAuthenticationPort {

    private val logger = LoggerFactory.getLogger(HubspotOAuthAdapter::class.java)

    override fun generateAuthorizationUrl(): String {
        return "https://app.hubspot.com/oauth/authorize" +
            "?client_id=${hubSpotProperties.clientId}" +
            "&redirect_uri=${hubSpotProperties.redirectUri}" +
            "&scope=${hubSpotProperties.scopes}"
    }

    override fun getAccessToken(code: String): OAuthToken {
        try {
            val response = hubSpotAuthClient.getAccessToken(
                grantType = "authorization_code",
                clientId = hubSpotProperties.clientId,
                clientSecret = hubSpotProperties.clientSecret,
                redirectUri = hubSpotProperties.redirectUri,
                code = code,
            )
            return OAuthToken(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                expiresIn = response.expiresIn,
                tokenType = response.tokenType,
                expired = response.isExpired(),
            )
        } catch (e: Exception) {
            logger.error("Error getting access token: ${e.message}", e)
            throw AuthenticationException(e.message, e.cause)
        }
    }

    override fun refreshAccessToken(refreshToken: String): OAuthToken {
        try {
            val response = hubSpotAuthClient.getAccessToken(
                grantType = "refresh_token",
                clientId = hubSpotProperties.clientId,
                clientSecret = hubSpotProperties.clientSecret,
                redirectUri = hubSpotProperties.redirectUri,
                code = refreshToken,
            )
            return OAuthToken(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                expiresIn = response.expiresIn,
                tokenType = response.tokenType,
                expired = response.isExpired(),
            )
        } catch (e: Exception) {
            logger.error("Error refreshing access token: ${e.message}", e)
            throw RefreshTokenException(e.message, e.cause)
        }
    }
}
