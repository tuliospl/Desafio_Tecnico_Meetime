package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.port.input.OAuthUseCase
import com.desafio.meetime.fixed.fixedOAuthToken
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class)
class OAuthControllerTest {
    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var oAuthUseCase: OAuthUseCase

    private lateinit var oAuthController: OAuthController

    @BeforeEach
    fun setup() {
        oAuthController = OAuthController(oAuthUseCase)
        mockMvc = MockMvcBuilders.standaloneSetup(oAuthController).build()
    }

    @Test
    fun `authorize endpoint should return redirect with authorization URL`() {
        val authorizationUrl = "https://example.com/oauth/authorize"
        every { oAuthUseCase.generateAuthorizationUrl() } returns authorizationUrl

        mockMvc.perform(get("/auth/authorize"))
            .andExpect(status().isFound)
            .andExpect(header().string(HttpHeaders.LOCATION, authorizationUrl))
    }

    @Test
    fun `callback endpoint should return OAuth token when valid code is provided`() {
        val authCode = "test-auth-code"
        val expectedToken = fixedOAuthToken()

        every { oAuthUseCase.authenticate(authCode) } returns expectedToken

        mockMvc.perform(get("/auth/callback")
            .param("code", authCode),
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `callback endpoint should handle missing code parameter`() {
        mockMvc.perform(get("/auth/callback"))
            .andExpect(status().isBadRequest)
    }
}
