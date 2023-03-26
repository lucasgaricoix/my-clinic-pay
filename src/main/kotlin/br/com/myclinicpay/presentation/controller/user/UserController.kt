package br.com.myclinicpay.presentation.controller.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.domain.usecases.user.UserContract
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class UserController(private val userService: UserContract) {
    fun createUser(user: User, aesUtil: AESUtil): ResponseEntity<User> {
        val userData = userService.createUser(user, aesUtil)
        return ResponseEntity<User>(userData, HttpStatus.CREATED)
    }

    fun updateUser(user: User, aesUtil: AESUtil): ResponseEntity<User> {
        val userData = userService.updateUser(user, aesUtil)
        return ResponseEntity<User>(userData, HttpStatus.OK)
    }

    fun login(credential: Credential): ResponseEntity<User> {
        val userData = userService.login(credential)
        return ResponseEntity<User>(userData, HttpStatus.OK)
    }
}
