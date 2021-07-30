package br.com.myclinicpay.presentation.factories.payment

import br.com.myclinicpay.data.service.payment.FindAllPaymentOverMonthService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.FindAllPaymentOverMonthRepository
import br.com.myclinicpay.presentation.controller.payment.PaymentController

class PaymentFactory {
    fun build(): PaymentController {
        val repository = FindAllPaymentOverMonthRepository()
        val service = FindAllPaymentOverMonthService(repository)
        return PaymentController(service)
    }
}
