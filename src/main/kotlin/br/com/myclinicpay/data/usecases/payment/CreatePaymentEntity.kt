package br.com.myclinicpay.data.usecases.payment

import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity

interface CreatePaymentEntity {
    fun create(paymentEntity: PaymentEntity) : PaymentEntity
}
