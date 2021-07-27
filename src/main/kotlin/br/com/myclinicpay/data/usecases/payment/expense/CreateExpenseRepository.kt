package br.com.myclinicpay.data.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person

interface CreateExpenseRepository {
    fun create(expense: Expense, paymentType: PaymentType) : Expense
}
