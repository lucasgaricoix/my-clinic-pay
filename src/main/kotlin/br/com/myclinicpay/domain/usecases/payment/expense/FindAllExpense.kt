package br.com.myclinicpay.domain.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface FindAllExpense {
    fun findAll(): List<Expense>
}
