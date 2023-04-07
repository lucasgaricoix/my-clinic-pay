package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity

interface CreatePersonRepository {
    fun create(person: Person) : PersonEntity
}
