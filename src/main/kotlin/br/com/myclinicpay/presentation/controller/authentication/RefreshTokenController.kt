package br.com.myclinicpay.presentation.controller.authentication

import br.com.myclinicpay.data.service.authentication.RefreshTokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RefreshTokenController(private val service: RefreshTokenService) {
    fun refresh(token: String): ResponseEntity<String> {
        val refreshToken = this.service.validateToken(token)
        return ResponseEntity<String>(refreshToken.token, HttpStatus.OK)
    }
}
