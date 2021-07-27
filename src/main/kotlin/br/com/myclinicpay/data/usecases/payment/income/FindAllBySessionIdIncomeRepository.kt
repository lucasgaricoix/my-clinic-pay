package br.com.myclinicpay.data.usecases.payment.income

import java.time.LocalDate

interface FindAllBySessionIdIncomeRepository {
    fun findAll(initialRange: LocalDate, finalRange: LocalDate): Int
}
