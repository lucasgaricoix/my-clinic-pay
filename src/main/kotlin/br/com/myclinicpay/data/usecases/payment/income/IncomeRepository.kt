package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity

interface IncomeRepository {
    fun create(income: Income, lastSession: Int?): Income

    fun findById(id: String) : IncomeEntity

    fun deleteById(id: String)

    fun deleteByScheduleId(scheduleId: String): String
}
