package br.com.myclinicpay.domain.usecases.payment.income

import br.com.myclinicpay.domain.model.payment.IncomeByPatient

interface FindAllIncomeByPatient {
    fun find(month: Int): List<IncomeByPatient>
}
