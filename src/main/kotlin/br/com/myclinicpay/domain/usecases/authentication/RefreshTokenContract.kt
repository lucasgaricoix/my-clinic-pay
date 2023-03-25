package br.com.myclinicpay.domain.usecases.authentication

import br.com.myclinicpay.infra.db.mongoDb.entities.RefreshTokenEntity

interface RefreshTokenContract {
    fun findByUserName(username: String): RefreshTokenEntity
    fun create(username: String?): RefreshTokenEntity
    fun validateToken(token: String): RefreshTokenEntity
}
