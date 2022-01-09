package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.FindAllPersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class FindAllPersonRepository : FindAllPersonRepository {
    private val collectionName = "person"
    override fun findAll(search: String?): List<Person> {
        val mongoTemplate = Connection.getTemplate()

        if (search != null) {
            val documentQuery = Query(
                Criteria.where("name").regex(search, "i")
            )
            return mongoTemplate.find(documentQuery, PersonEntity::class.java, collectionName)
                .map { adapter(it) }
                .toList()
        }

        val personEntity = mongoTemplate.findAll<PersonEntity>(collectionName)
        return personEntity.map { adapter(it) }
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
