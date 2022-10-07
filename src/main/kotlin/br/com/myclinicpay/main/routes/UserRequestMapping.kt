package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.presentation.factories.user.UserFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserRequestMapping {
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        return UserFactory().build().createUser(user)
    }
}
