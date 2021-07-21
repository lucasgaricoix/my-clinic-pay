package br.com.myclinicpay.infra.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.FindAllPaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.mongoDb.Connection
import br.com.myclinicpay.infra.mongoDb.entities.PaymentTypeEntity
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class FindAllPaymentTypesRepository : FindAllPaymentTypeRepository {
    private val collectionName = "payment_type"
    override fun findAllPaymentType(): List<PaymentType> {
        val mongoTemplate = Connection.getTemplate()
        val query = Query()

        return mongoTemplate.find(query, PaymentTypeEntity::class.java, collectionName)
            .map { adapter(it) }
            .toList()
    }

    private fun adapter(paymentType: PaymentTypeEntity): PaymentType {
        return PaymentType(
            paymentType.id.toString(),
            paymentType.description,
            paymentType.value
        )
    }
}
