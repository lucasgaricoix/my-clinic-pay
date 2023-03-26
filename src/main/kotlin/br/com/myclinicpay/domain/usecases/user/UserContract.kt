package br.com.myclinicpay.domain.usecases.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User

interface UserContract {
    fun createUser(user: User, aesUtil: AESUtil): User

    fun updateUser(user: User, aesUtil: AESUtil): User

    fun findUserById(id: String): User

    fun findByUserEmail(email: String?): User

    fun login(credential: Credential): User
}
