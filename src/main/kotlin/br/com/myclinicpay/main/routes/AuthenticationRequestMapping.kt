package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.presentation.factories.user.UserFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
