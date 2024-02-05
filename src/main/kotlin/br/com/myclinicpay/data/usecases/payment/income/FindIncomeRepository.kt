package br.com.myclinicpay.data.usecases.payment.income

import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import java.time.LocalDate

interface FindIncomeRepository {
    fun findById(id: String) : IncomeEntity

    fun findAll(month: Int, year: Int): List<IncomeEntity>

    fun findLastSession(initialRange: LocalDate, finalRange: LocalDate): Int
}
