package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity

interface FindAllIncomeRepository {
    fun findAll(month: Int, year: Int): List<IncomeEntity>
}
