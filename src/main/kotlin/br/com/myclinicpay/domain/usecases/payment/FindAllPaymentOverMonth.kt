package br.com.myclinicpay.domain.usecases.payment

import br.com.myclinicpay.domain.model.payment.PaymentOverMonth

interface FindAllPaymentOverMonth {
    fun findAllOverMonth(): List<PaymentOverMonth>
}
