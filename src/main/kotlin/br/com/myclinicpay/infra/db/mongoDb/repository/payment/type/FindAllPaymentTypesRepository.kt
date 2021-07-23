package br.com.myclinicpay.infra.db.mongoDb.repository.payment.type

import br.com.myclinicpay.data.usecases.payment.type.FindAllPaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.springframework.stereotype.Repository

@Repository
class FindAllPaymentTypesRepository : FindAllPaymentTypeRepository {
    private val collectionName = "payment_type"
    override fun findAllPaymentType(): List<PaymentType> {
        val mongoTemplate = Connection.getTemplate()

        return mongoTemplate.findAll(PaymentTypeEntity::class.java, collectionName)
            .map { adapter(it) }
            .toList()
    }

    private fun adapter(paymentTypeEntity: PaymentTypeEntity): PaymentType {
        return PaymentType(
            paymentTypeEntity.id.toString(),
            TypeEnum.valueOf(paymentTypeEntity.type.uppercase()),
            paymentTypeEntity.description,
            paymentTypeEntity.value
        )
    }
}
