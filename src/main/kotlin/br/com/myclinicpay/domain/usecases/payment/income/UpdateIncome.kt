package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface UpdateIncome {
    fun update(income: Income) : String
}
