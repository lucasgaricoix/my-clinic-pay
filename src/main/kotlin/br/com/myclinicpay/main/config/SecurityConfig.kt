package br.com.myclinicpay.main.config

import br.com.myclinicpay.data.service.authentication.*
import br.com.myclinicpay.data.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @Autowired
    private lateinit var aesUtil: AESUtil

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/auth/signup")
            .permitAll()
            .anyRequest()
            .authenticated()

        http.addFilter(JWTAuthenticationFilter(authenticationManager(), jwtUtil, refreshTokenService))
        http.addFilter(JWTRefreshTokenFilter(authenticationManager(), jwtUtil, refreshTokenService, aesUtil))
        http.addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil, userService))

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(aesUtil)
    }
}
