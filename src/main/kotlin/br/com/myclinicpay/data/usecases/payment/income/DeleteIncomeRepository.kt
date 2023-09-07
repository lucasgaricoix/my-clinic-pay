package br.com.myclinicpay.data.usecases.payment.income

interface DeleteIncomeRepository {
    fun deleteById(id: String)
}
