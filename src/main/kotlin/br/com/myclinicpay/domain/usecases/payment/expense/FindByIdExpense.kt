package br.com.myclinicpay.domain.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface FindByIdExpense {
    fun findById(id: String): Expense
}
