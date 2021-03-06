package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class FindPersonByIdRepository : FindPersonByIdRepository {
    private val collectionName = "person"
    override fun findById(id: String): Person {
        val mongoTemplate = Connection.getTemplate()
        val personEntity = mongoTemplate.findById<PersonEntity>(ObjectId(id), collectionName)
            ?: throw Exception("Não foi possível encontrar a pessoa com o id $id")
        return adapter(personEntity)
    }

    private fun adapter(personEntity: PersonEntity): Person {
        return Person(
            personEntity.id.toString(),
            personEntity.name,
            personEntity.birthDate,
            Responsible(
                personEntity.responsible.id.toString(),
                personEntity.responsible.name
            )
        )
    }
}
