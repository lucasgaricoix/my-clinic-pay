package br.com.myclinicpay.main.routes

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.presentation.factories.user.UserFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserRequestMapping {

    @Autowired
    private lateinit var aesUtil: AESUtil

    @PatchMapping
    fun updateUser(@RequestBody user: User): ResponseEntity<User> {
        return UserFactory().build().updateUser(user, aesUtil)
    }
}
