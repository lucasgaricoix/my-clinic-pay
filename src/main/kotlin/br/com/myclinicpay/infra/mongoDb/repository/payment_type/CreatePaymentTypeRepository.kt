package br.com.myclinicpay.infra.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.CreatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.mongoDb.Connection
import br.com.myclinicpay.infra.mongoDb.entities.PaymentTypeEntity
import org.springframework.stereotype.Repository

@Repository
class CreatePaymentTypeRepository : CreatePaymentTypeRepository {
    val collectionName = "payment_type"
    override fun save(paymentType: PaymentType): PaymentType {
        val mongoTemplate = Connection.getTemplate()
        val typeValue = PaymentTypeEntity(null, paymentType.description, paymentType.value)
        val createdTypeValue = mongoTemplate.save(typeValue, collectionName)
        return adapter(createdTypeValue)
    }

    private fun adapter(paymentType: PaymentTypeEntity): PaymentType {
        return PaymentType(
            paymentType.id.toString(),
            paymentType.description,
            paymentType.value
        )
    }
}
