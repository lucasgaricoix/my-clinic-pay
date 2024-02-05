package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.FindPersonRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.stereotype.Repository

@Repository
class FindPersonRepository : FindPersonRepository {
    private val collectionName = "person"
    override fun findById(id: String): PersonEntity {
        val mongodbTemplate = Connection.getTemplate()
        return mongodbTemplate.findById<PersonEntity>(ObjectId(id), collectionName)
            ?: throw Exception("Não foi possível encontrar a pessoa com o id $id")
    }
}
