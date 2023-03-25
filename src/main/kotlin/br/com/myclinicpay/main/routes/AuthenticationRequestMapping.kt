package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.presentation.factories.authentication.RefreshTokenFactory
import br.com.myclinicpay.presentation.factories.user.UserFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationRequestMapping {

    @PostMapping("signup")
    fun signup(@RequestBody user: User): ResponseEntity<User> {
        return UserFactory().build().createUser(user)
    }

    @PostMapping("login")
    fun login(@RequestBody credential: Credential): ResponseEntity<User> {
        return UserFactory().build().login(credential)
    }

    @PostMapping("refresh-token")
    fun refresh(@RequestParam token: String): ResponseEntity<String> {
        return RefreshTokenFactory().build().refresh(token)
    }
}
