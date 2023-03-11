package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.CreatePersonRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ResponsibleEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

@Repository
class CreatePersonRepository : CreatePersonRepository {
    private val collectionName = "person"
    override fun create(person: Person): Person {
        val mongodbTemplate = Connection.getTemplate()
        val created = mongodbTemplate.save<PersonEntity>(personEntityAdapter(person), collectionName)

        return adapter(created)
    }

    private fun personEntityAdapter(person: Person): PersonEntity {
        return PersonEntity(
            ObjectId.get(),
            person.name,
            person.birthDate,
            ResponsibleEntity(
                ObjectId.get(),
                person.responsible.name
            ),
            person.paymentType?.let {
                PaymentTypeEntity(
                    ObjectId.get(),
                    it.type.value,
                    it.description,
                    it.value
                )
            }
        )
    }

    private fun adapter(personEntity: PersonEntity): Person {
        return Person(
            personEntity.id.toString(),
            personEntity.name,
            personEntity.birthDate,
            Responsible(
                personEntity.responsible.id.toString(),
                personEntity.responsible.name
            ),
            personEntity.paymentType?.let {
                PaymentType(
                    it.id.toString(),
                    TypeEnum.valueOf(it.type),
                    it.description,
                    it.value
                )
            }
        )
    }
}
