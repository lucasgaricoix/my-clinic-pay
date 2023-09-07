package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.UpdatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpServerErrorException

class UpdatePersonRepository : UpdatePersonRepository {
    private val collectionName = "person"
    override fun update(person: Person): String {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("_id").isEqualTo(person.id))
        val toUpdate = Update()
            .set("name", person.name)
            .set("birthDate", person.birthDate)
            .set("responsible", person.responsible)
            .set("paymentTypeId", person.paymentType?.id)

        val updated = mongodbTemplate.updateFirst(query, toUpdate, collectionName)
        if (updated.modifiedCount <= 0) {
            throw HttpServerErrorException(HttpStatus.BAD_REQUEST, "Não houve modificação.")
        }
        return updated.upsertedId.toString()
    }
}
