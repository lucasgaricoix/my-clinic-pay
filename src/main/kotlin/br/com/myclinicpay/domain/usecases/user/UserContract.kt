package br.com.myclinicpay.domain.usecases.user

import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User

interface UserContract {
    fun createUser(user: User): User

    fun updateUser(user: User): User

    fun findUserById(id: String): User

    fun findByUserEmail(email: String?): User

    fun login(credential: Credential): User
}
