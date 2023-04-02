package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface CreateIncomeRepository {
    fun create(income: Income, lastSession: Int?): Income
}
