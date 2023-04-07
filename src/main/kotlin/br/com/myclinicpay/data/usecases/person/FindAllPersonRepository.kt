package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity

interface FindAllPersonRepository {
    fun findAll(search: String?) : List<PersonEntity>
}
