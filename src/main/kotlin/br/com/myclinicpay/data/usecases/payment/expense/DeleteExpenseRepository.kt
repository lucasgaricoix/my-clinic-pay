package br.com.myclinicpay.data.usecases.payment.expense

interface DeleteExpenseRepository {
    fun deleteById(id: String)
}
