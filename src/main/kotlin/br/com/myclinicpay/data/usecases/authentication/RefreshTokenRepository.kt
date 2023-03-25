package br.com.myclinicpay.data.usecases.authentication

import br.com.myclinicpay.infra.db.mongoDb.entities.RefreshTokenEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import java.time.LocalDateTime

interface RefreshTokenRepository {

    fun findByToken(token: String): RefreshTokenEntity?

    fun findByUsername(username: String): RefreshTokenEntity?

    fun create(user: UserEntity, token: String, expirationDate: LocalDateTime): RefreshTokenEntity

    fun deleteById(id: String): Boolean
}
