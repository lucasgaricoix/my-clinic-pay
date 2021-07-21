package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface FindAllPersonRepository {
    fun findAll() : List<Person>
}
