package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity

interface FindPersonRepository {
    fun findById(id: String) : PersonEntity
}
