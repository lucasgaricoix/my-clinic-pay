package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface CreatePersonRepository {
    fun create(person: Person) : Person
}
