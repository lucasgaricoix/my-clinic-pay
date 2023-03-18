package br.com.myclinicpay.data.usecases.user

import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity

interface UserRepository {
    fun createUser(user: User): UserEntity

    fun updateUser(user: User): UserEntity

    fun findByEmail(email: String): List<UserEntity>

    fun findById(id: String): UserEntity?
}
