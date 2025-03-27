package com.desafio.meetime.infrastructure.adapter

import com.desafio.meetime.domain.model.OAuthToken
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "hubSpotAuthClient", url = "https://api.hubapi.com")
interface HubSpotAuthClient {

    @PostMapping("/oauth/v1/token", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getAccessToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,): OAuthToken
}
