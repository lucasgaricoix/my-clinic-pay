package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.CreatePerson
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.mongoDb.entities.ResponsibleEntity
import br.com.myclinicpay.infra.mongoDb.repository.person.PersonRepository
import org.springframework.stereotype.Service

@Service
class CreatePersonService(val repository: PersonRepository): CreatePerson {

    override fun create(person: Person): Person {
        val person = repository.save(this.adapter(person))
        return Person(person.name, person.birthDate, person.age, Responsible(person.responsible.name))
    }

    private fun adapter(person: Person): PersonEntity {
        val responsible = ResponsibleEntity(null, person.responsible.name)
        return PersonEntity(null, person.name, person.birthDate, person.age, responsible)
    }
}