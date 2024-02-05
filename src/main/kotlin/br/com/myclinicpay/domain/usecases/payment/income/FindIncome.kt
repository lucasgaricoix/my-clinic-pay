package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import java.time.LocalDate

interface FindIncome {
    fun findById(id: String) : Income

    fun findAll(month: Int, year: Int) : List<Income>

    fun findByMonthAndYear(month: Int, year: Int): List<IncomeByPatient>

    fun findLastSessionByDateRange(initialRange: LocalDate, finalRange: LocalDate): Int
}
