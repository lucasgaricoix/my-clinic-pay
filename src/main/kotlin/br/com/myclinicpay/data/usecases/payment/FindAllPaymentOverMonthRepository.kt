package br.com.myclinicpay.data.usecases.payment

import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity
import java.time.LocalDate

interface FindAllPaymentOverMonthRepository {
    fun findAllOverMonth(initialDate: LocalDate, finalDate: LocalDate): List<PaymentEntity>
}
