package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface CreatePerson {
    fun create(person: Person) : Person
}