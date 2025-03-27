package com.desafio.meetime.fixed

import com.desafio.meetime.domain.model.OAuthToken

fun fixedOAuthToken() = OAuthToken(
    accessToken = "accessToken",
    refreshToken = "refreshToken",
    expiresIn = 0,
    tokenType = "Bearer",
    expired = false,
)
