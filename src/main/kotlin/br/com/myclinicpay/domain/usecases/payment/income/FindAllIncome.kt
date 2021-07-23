package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface FindAllIncome {
    fun findAll() : List<Income>
}
