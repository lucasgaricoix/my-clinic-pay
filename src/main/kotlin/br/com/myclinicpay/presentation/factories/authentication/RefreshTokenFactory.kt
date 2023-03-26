package br.com.myclinicpay.presentation.factories.authentication

import br.com.myclinicpay.data.service.authentication.RefreshTokenService
import br.com.myclinicpay.infra.db.mongoDb.repository.authentication.RefreshTokenRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import br.com.myclinicpay.presentation.controller.authentication.RefreshTokenController

class RefreshTokenFactory {
    fun build(): RefreshTokenController {
        val refreshTokenRepository = RefreshTokenRepository()
        val userRepository = UserRepository()
        val service = RefreshTokenService(refreshTokenRepository, userRepository)
        return RefreshTokenController(service)
    }
}
