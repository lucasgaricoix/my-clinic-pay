package br.com.myclinicpay.infra.db.mongoDb.repository.person

import br.com.myclinicpay.data.usecases.person.DeletePersonRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.Document
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

class DeletePersonRepository : DeletePersonRepository {
    private val collectionName = "person"
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun deleteById(id: String) {
        val mongoTemplate = Connection.getTemplate()
        val query = Document("_id", ObjectId(id))
        val deleted = mongoTemplate.getCollection(collectionName).deleteOne(query)
        if (deleted.wasAcknowledged()) {
            logger.warn("Não foi encontrado a pessoa para exclusão.")
        }
    }
}
