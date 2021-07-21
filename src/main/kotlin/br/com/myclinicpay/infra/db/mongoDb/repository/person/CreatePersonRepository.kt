package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.CreatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ResponsibleEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

@Repository
class CreatePersonRepository : CreatePersonRepository {
    private val collectionName = "person"
    override fun create(person: Person): Person {
        val mongoTemplate = Connection.getTemplate()
        val created = mongoTemplate.save<PersonEntity>(personEntityAdapter(person), collectionName)

        return adapter(created)
    }

    private fun personEntityAdapter(person: Person): PersonEntity {
        return PersonEntity(
            ObjectId.get(),
            person.name,
            person.birthDate,
            person.age,
            ResponsibleEntity(
                ObjectId.get(),
                person.responsible.name
            )
        )
    }

    private fun adapter(personEntity: PersonEntity): Person {
        return Person(
            personEntity.id.toString(),
            personEntity.name,
            personEntity.birthDate,
            personEntity.age,
            Responsible(
                personEntity.responsible.id.toString(),
                personEntity.responsible.name
            )
        )
    }
}
