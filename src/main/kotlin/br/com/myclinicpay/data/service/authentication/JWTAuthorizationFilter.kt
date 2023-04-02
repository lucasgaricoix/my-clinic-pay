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
) : BasicAuthenticationFilter(
    authenticationManager
) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

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
}
