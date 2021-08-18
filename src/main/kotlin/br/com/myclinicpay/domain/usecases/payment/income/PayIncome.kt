package br.com.myclinicpay.domain.usecases.payment.income

interface PayIncome {
    fun pay(id: String) : String
}
