package br.com.myclinicpay.infra.db.mongoDb.repository.payment.type

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.stereotype.Repository

@Repository
class FindPaymentTypeByIdRepository : FindPaymentTypeByIdRepository {
    val collectionName = "payment_type"
    override fun findById(id: String): PaymentTypeEntity? {
        val mongodbTemplate = Connection.getTemplate()
        return mongodbTemplate.findById(id, collectionName)
    }
}
