package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person

interface CreateIncomeRepository {
    fun create(income: Income, paymentType: PaymentType, person: Person, nextSession: Int): Income
}
