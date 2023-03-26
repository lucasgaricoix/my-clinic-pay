package br.com.myclinicpay.data.service.authentication

import br.com.myclinicpay.data.usecases.authentication.RefreshTokenRepository
import br.com.myclinicpay.domain.usecases.authentication.RefreshTokenContract
import br.com.myclinicpay.infra.db.mongoDb.entities.RefreshTokenEntity
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDateTime
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository
) : RefreshTokenContract {
    override fun findByUserName(username: String): RefreshTokenEntity {
        return this.refreshTokenRepository.findByUsername(username)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o token.")
    }

    override fun create(username: String?): RefreshTokenEntity {
        if (username == null) {
            throw HttpServerErrorException(HttpStatus.BAD_REQUEST, "Informar o nome do usuário.")
        }
        val user = userRepository.findByEmail(username)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o usuário.")

        val refreshToken = this.refreshTokenRepository.findByUsername(username)

        if (refreshToken == null) {
            val token = UUID.randomUUID().toString()
            val expirationDate = LocalDateTime.now().plusMonths(1)

            return this.refreshTokenRepository.create(user, token, expirationDate)
        }

        return refreshToken
    }

    override fun validateToken(token: String): RefreshTokenEntity {
        val refreshToken = this.refreshTokenRepository.findByToken(token)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o token.")

        if (refreshToken.expirationDate.isBefore(LocalDateTime.now())) {
            val deleted = this.refreshTokenRepository.deleteById(refreshToken.id.toString())

            if (!deleted) {
                throw HttpServerErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Não foi possível deletar o token expirado."
                )
            }

            throw HttpServerErrorException(HttpStatus.FORBIDDEN, "Não foi possível deletar o token expirado.")
        }

        return refreshToken
    }
}
