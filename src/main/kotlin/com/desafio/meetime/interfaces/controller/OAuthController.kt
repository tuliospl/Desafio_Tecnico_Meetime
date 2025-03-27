package com.desafio.meetime.interfaces.controller

import com.desafio.meetime.domain.model.OAuthToken
import com.desafio.meetime.domain.port.input.OAuthUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class OAuthController(private val oAuthUseCase: OAuthUseCase) {

    @GetMapping("/authorize")
    fun authorize(): ResponseEntity<Map<String, String>> {
        val authUrl = oAuthUseCase.generateAuthorizationUrl()
        return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, authUrl)
            .build()
    }

    @GetMapping("/callback")
    fun callback(@RequestParam code: String): ResponseEntity<OAuthToken> {
        val token = oAuthUseCase.authenticate(code)
        return ResponseEntity.ok(token)
    }
}
