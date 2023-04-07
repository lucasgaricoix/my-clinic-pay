package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity

interface FindPersonByIdRepository {
    fun findById(id: String) : PersonEntity
    fun findEntityById(id: String) : PersonEntity
}
