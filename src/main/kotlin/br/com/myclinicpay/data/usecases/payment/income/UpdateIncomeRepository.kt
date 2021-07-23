package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface UpdateIncomeRepository {
    fun update(income: Income): String
}
