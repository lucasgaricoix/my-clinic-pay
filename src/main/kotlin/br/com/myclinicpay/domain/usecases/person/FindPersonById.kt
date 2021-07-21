package br.com.myclinicpay.domain.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface FindPersonById {
    fun findById(id: String) : Person
}
