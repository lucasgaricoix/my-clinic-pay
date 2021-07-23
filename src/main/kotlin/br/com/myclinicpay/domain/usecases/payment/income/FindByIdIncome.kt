package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface FindByIdIncome {
    fun findById(id: String) : Income
}
