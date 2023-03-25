package br.com.myclinicpay.data.service.authentication

import br.com.myclinicpay.data.service.user.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private var jwtUtil: JWTUtil,
    private var userService: UserService,
    private var refreshTokenService: RefreshTokenService
) : BasicAuthenticationFilter(
    authenticationManager
) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        val refreshToken = request.getHeader("Refresh-Token")

        if (refreshToken != null) {
            val validToken = this.refreshTokenService.validateToken(refreshToken)
            val auth = getAuthenticationByRefreshToken(validToken.user.email)
            SecurityContextHolder.getContext().authentication = auth
            chain.doFilter(request, response)
            return
        }

        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }

        if (header.startsWith("Bearer")) {
            val auth = getAuthentication(header)
            SecurityContextHolder.getContext().authentication = auth
            chain.doFilter(request, response)
        }
    }

    private fun getAuthentication(authorizationHeader: String?): UsernamePasswordAuthenticationToken {
        val token = authorizationHeader?.substring(7) ?: ""
        if (jwtUtil.isTokenValid(token)) {
            val email = jwtUtil.getUserEmail(token)
            val userDetails = userService.loadUserByUsername(email)
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }

        throw Exception("Auth invalid")
    }

    private fun getAuthenticationByRefreshToken(username: String?): UsernamePasswordAuthenticationToken {
        val userDetails = userService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

}
