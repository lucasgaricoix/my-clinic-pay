package br.com.myclinicpay.presentation.factories.user


import br.com.myclinicpay.data.service.user.UserService
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import br.com.myclinicpay.presentation.controller.user.UserController

class UserFactory {
    fun build(): UserController {
        val userRepository = UserRepository()
        val userService = UserService(userRepository)
        return UserController(userService)
    }
}
