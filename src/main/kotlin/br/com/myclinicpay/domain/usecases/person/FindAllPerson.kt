package br.com.myclinicpay.domain.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface FindAllPerson {
    fun findAll(search: String) : List<Person>
}
