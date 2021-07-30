package br.com.myclinicpay.domain.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface UpdatePerson {
    fun update(person: Person): String
}
