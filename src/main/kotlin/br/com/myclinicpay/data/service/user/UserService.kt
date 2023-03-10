package br.com.myclinicpay.data.service.user

import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.domain.usecases.user.UserContract
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

@Service
class UserService(private val userRepository: UserRepository) : UserContract {
    override fun createUser(user: User): User {
        val createdUser = userRepository.createUser(user)
        return adapter(createdUser)
    }

    override fun findUserById(id: String): User {
        val userEntity = userRepository.findById(id)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi encontrado o usuário")

        return adapter(userEntity)
    }

    private fun adapter(userEntity: UserEntity): User {
        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.email,
            userEntity.picture,
            userEntity.role,
            userEntity.id.toString()
        )
    }
}
