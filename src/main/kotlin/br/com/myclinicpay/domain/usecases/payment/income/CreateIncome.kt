package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface CreateIncome {
    fun create(income: Income) : Income

    fun findLastSessionId(): Int
}
