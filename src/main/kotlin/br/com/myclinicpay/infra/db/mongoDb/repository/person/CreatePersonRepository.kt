package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.CreatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import org.springframework.stereotype.Repository

@Repository
class CreatePersonRepository() :
    CreatePersonRepository {
    private val collectionName = "person"
    override fun create(person: Person): PersonEntity {
        val mongodbTemplate = Connection.getTemplate()
        return mongodbTemplate.save<PersonEntity>(person.toEntity(), collectionName)
    }
}
