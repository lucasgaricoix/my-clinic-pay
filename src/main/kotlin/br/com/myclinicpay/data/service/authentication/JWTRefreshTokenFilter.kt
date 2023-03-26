package br.com.myclinicpay.data.service.authentication

import br.com.myclinicpay.domain.model.user.UserDetailsImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.HttpServerErrorException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTRefreshTokenFilter(
    authenticationManager: AuthenticationManager,
    private var jwtUtil: JWTUtil,
    private var refreshTokenService: RefreshTokenService,
    private var aesUtil: AESUtil
) : UsernamePasswordAuthenticationFilter() {
    init {
        this.authenticationManager = authenticationManager
        setFilterProcessesUrl("/api/auth/refresh-token")
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val refreshToken = request.getHeader("Refresh-token")

            val refreshTokenEntity = this.refreshTokenService.validateToken(refreshToken)

            val token = UsernamePasswordAuthenticationToken(
                refreshTokenEntity.user.email,
                this.decryptPassword(refreshTokenEntity.user.password),
                mutableListOf()
            )

            return authenticationManager.authenticate(token)
        } catch (error: Exception) {
            throw HttpServerErrorException(HttpStatus.FORBIDDEN, "Failed to attempt authentication. \n\n $error")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val userDetails = (authResult.principal as UserDetailsImpl)
        val userName = userDetails.username
        val token = jwtUtil.generateToken(userName, userDetails.getId(), userDetails.getName())
        val refreshToken = refreshTokenService.create(userName)
        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("Refresh-token", jwtUtil.generateRefreshTokenCookie(refreshToken.token).toString())
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        failed: AuthenticationException?
    ) {
        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

    private fun decryptPassword(password: String): String {
        val key = aesUtil.getKeyFromPassword()
        val ivParameterSpec = aesUtil.getIvParameterSpec()
        return aesUtil.decrypt(password, key, ivParameterSpec)
    }

    private data class BadCredentialsError(
        val timestamp: Long = Date().time,
        val status: Int = 401,
        val message: String = "Email or password incorrect",
    ) {
        override fun toString(): String {
            return ObjectMapper().writeValueAsString(this)
        }
    }
}
