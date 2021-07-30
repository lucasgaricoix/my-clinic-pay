package br.com.myclinicpay.infra.db.mongoDb.repository.payment

import br.com.myclinicpay.data.usecases.payment.CreatePaymentEntity
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity

class CreatePaymentRepository : CreatePaymentEntity {
    override fun create(paymentEntity: PaymentEntity): PaymentEntity {
        val mongoTemplate = Connection.getTemplate()
        return mongoTemplate.save(paymentEntity, "payment")
    }
}
