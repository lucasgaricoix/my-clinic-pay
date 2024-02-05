package br.com.myclinicpay.data.usecases.payment.type

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity

interface FindPaymentTypeByIdRepository {
    fun findById(id: String) : PaymentTypeEntity?
}
