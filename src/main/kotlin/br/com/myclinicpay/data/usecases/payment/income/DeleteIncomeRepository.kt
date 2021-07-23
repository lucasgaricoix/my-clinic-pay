package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income

interface DeleteIncomeRepository {
    fun deleteById(id: String)
}
