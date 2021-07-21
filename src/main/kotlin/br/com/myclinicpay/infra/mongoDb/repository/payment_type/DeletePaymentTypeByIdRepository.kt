package br.com.myclinicpay.infra.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.infra.mongoDb.Connection
import org.bson.Document
import org.springframework.stereotype.Repository

@Repository
class DeletePaymentTypeByIdRepository : DeletePaymentTypeByIdRepository {
    val collectionName = "payment_type"
    override fun deleteById(id: String) {
        val mongoCollection = Connection.getConnection().getCollection(collectionName)
        val filter = Document("id", id)
        mongoCollection.deleteOne(filter)
    }
}
