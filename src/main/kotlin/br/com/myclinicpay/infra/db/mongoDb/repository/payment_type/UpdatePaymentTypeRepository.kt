package br.com.myclinicpay.infra.db.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.UpdatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

@Repository
class UpdatePaymentTypeRepository : UpdatePaymentTypeRepository {
    val collectionName = "payment_type"
    override fun updateById(paymentType: PaymentType): String {
        try {
            val mongoCollection = Connection.getTemplate().getCollection(collectionName)
            val query = Document("_id", ObjectId(paymentType.id))
            val toUpdate = Document("description", paymentType.description)
                .append("type", paymentType.type.value)
                .append("value", paymentType.value)
            val updateQuery = Document("\$set", toUpdate)
            val update = mongoCollection.updateOne(query, updateQuery)

            if (!update.wasAcknowledged()) {
                throw Exception("Não foi possível fazer a alteração")
            }
            return update.upsertedId.toString()
        } catch (error: Exception) {
            throw Exception("Não foi possível fazer a alteração", error)
        }
    }
}
