package br.com.myclinicpay.data.service.user

import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.domain.usecases.user.UserContract
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) : UserContract {
    override fun createUser(user: User): User {
        val createdUser = userRepository.createUser(user)
        return adapter(createdUser)
    }

    private fun adapter(userEntity: UserEntity): User {
        return User(
            userEntity.name,
            userEntity.email,
            userEntity.picture,
            userEntity.role,
            userEntity.id.toString()
        )
    }
}
