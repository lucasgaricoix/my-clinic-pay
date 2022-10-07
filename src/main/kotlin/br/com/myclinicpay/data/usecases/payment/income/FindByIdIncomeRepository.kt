package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity

interface FindByIdIncomeRepository {
    fun findById(id: String) : IncomeEntity
}
