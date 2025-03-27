package com.desafio.meetime.infrastructure.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.ServletWebRequest

class GlobalExceptionHandlerTest {

    private val exceptionHandler = GlobalExceptionHandler()
    private val webRequest = ServletWebRequest(MockHttpServletRequest())

    @Test
    fun `handle ContactSaveException should return correct error response`() {
        val exception = ContactSaveException("Contact save failed")

        val responseEntity = exceptionHandler.handleContactSaveException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("Failed to save contact: Contact save failed", responseEntity.body?.message)
    }

    @Test
    fun `handle AuthenticationException should return correct error response`() {
        val exception = AuthenticationException("Invalid credentials")

        val responseEntity = exceptionHandler.handleAuthenticationException(exception, webRequest)

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.body?.code)
        assertEquals("Unauthorized: Invalid credentials", responseEntity.body?.message)
    }

    @Test
    fun `handle generic Exception should return correct error response`() {
        val exception = RuntimeException("Unexpected error occurred")

        val responseEntity = exceptionHandler.handleGenericException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("An unexpected error occurred: Unexpected error occurred", responseEntity.body?.message)
    }

    @Test
    fun `handle RefreshTokenException should return correct error response`() {
        val exception = RefreshTokenException("Token refresh failed")

        val responseEntity = exceptionHandler.handleRefreshTokenException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("Failed to refresh token: Token refresh failed", responseEntity.body?.message)
    }

    @Test
    fun `handle WebhookContactCreationSaveException should return correct error response`() {
        val exception = WebhookContactCreationSaveException("Webhook contact save failed")

        val responseEntity = exceptionHandler.handleWebhookContactCreationSaveException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("Failed to save webhook contact: Webhook contact save failed", responseEntity.body?.message)
    }

    @Test
    fun `handle SaveTokenException should return correct error response`() {
        val exception = SaveTokenException("Token save failed")

        val responseEntity = exceptionHandler.handleSaveTokenException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("Failed to save token: Token save failed", responseEntity.body?.message)
    }

    @Test
    fun `handle GetTokenException should return correct error response`() {
        val exception = GetTokenException("Token retrieval failed")

        val responseEntity = exceptionHandler.handleGetTokenException(exception, webRequest)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.body?.code)
        assertEquals("Failed to get token: Token retrieval failed", responseEntity.body?.message)
    }
}
