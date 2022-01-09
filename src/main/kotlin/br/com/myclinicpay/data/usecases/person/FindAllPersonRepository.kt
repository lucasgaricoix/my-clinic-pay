package br.com.myclinicpay.data.usecases.person

import br.com.myclinicpay.domain.model.person.Person

interface FindAllPersonRepository {
    fun findAll(search: String?) : List<Person>
}
