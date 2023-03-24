package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface UpdatePersonRepository {
    fun update(person: Person) : String
}
