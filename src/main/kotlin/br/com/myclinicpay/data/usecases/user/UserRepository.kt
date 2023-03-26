package br.com.myclinicpay.data.usecases.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity

interface UserRepository {
    fun createUser(user: User, aesUtil: AESUtil): UserEntity

    fun updateUser(user: User, aesUtil: AESUtil): UserEntity

    fun findByEmail(email: String): UserEntity?

    fun findById(id: String): UserEntity?

    fun findByName(name: String) : UserEntity?
}
