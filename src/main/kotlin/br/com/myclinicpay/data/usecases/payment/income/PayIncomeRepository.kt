package br.com.myclinicpay.data.usecases.payment.income

interface PayIncomeRepository {
    fun pay(id: String): String
}
