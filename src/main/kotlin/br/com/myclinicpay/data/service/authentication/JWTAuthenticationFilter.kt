package br.com.myclinicpay.data.service.authentication

import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.UserDetailsImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.HttpServerErrorException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private var jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {
    init {
        this.authenticationManager = authenticationManager
        setFilterProcessesUrl("/api/auth/login")
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val user = ObjectMapper().readValue(request.inputStream, Credential::class.java)

            val token = UsernamePasswordAuthenticationToken(user.username, user.password, mutableListOf())

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
        response.addHeader("Authorization", "Bearer $token")
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
