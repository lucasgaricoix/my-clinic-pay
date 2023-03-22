package br.com.myclinicpay.data.service.user

import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.user.Credential
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.domain.model.user.UserDetailsImpl
import br.com.myclinicpay.domain.usecases.user.UserContract
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

@Service
class UserService(private val userRepository: UserRepository) : UserContract, UserDetailsService {
    override fun createUser(user: User): User {
        val userExists = userRepository.findByEmail(user.email)

        if (userExists == null) {
            val createdUser = userRepository.createUser(user)
            return user.fromEntity(createdUser)
        }

        return userExists.toModel()
    }

    override fun updateUser(user: User): User {
        val updatedUser = userRepository.updateUser(user)
        return updatedUser.toModel()
    }

    override fun findUserById(id: String): User {
        val userEntity = userRepository.findById(id)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi encontrado o usuário")

        return userEntity.toModel()
    }

    override fun findByUserEmail(email: String?): User {
        val userEntity = userRepository.findByEmail(email!!)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi encontrado o usário por email")

        return userEntity.toModel()
    }

    override fun login(credential: Credential): User {
        val userEntity = this.findByUserEmail(credential.username)

        if (userEntity.password != credential.password) {
            throw HttpServerErrorException(HttpStatus.FORBIDDEN, "As senhas não conferem")
        }

        return userEntity
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = this.findByUserEmail(username)

        return UserDetailsImpl(user)
    }
}
