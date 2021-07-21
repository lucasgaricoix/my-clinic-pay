package br.com.myclinicpay.infra.db.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.Document
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class DeletePaymentTypeByIdRepository : DeletePaymentTypeByIdRepository {
    val collectionName = "payment_type"
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun deleteById(id: String) {
        val mongoCollection = Connection.getTemplate().getCollection(collectionName)
        val filter = Document("_id", ObjectId(id))
        val deleted = mongoCollection.findOneAndDelete(filter)
        if (deleted.isNullOrEmpty()) {
            logger.info("Nenhum documento excluído.")
        } else {
            logger.info("Excluído com sucesso.")
        }
    }
}
