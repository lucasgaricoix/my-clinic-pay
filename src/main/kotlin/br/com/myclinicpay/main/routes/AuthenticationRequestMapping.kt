package br.com.myclinicpay.main.routes

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.presentation.controller.authentication.RefreshTokenController
import br.com.myclinicpay.presentation.controller.user.UserController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationRequestMapping @Autowired constructor(
    private val userController: UserController,
    private val refreshTokenController: RefreshTokenController
) {

    @Autowired
    private lateinit var aesUtil: AESUtil

    @PostMapping("signup")
    fun signup(@RequestBody user: User): ResponseEntity<User> {
        return userController.createUser(user, aesUtil)
    }

    @PostMapping("login")
    fun login(@RequestBody credential: Credential): ResponseEntity<User> {
        return userController.login(credential)
    }

    @PostMapping("refresh-token")
    fun refresh(@RequestParam token: String): ResponseEntity<String> {
        return refreshTokenController.refresh(token)
    }
}
