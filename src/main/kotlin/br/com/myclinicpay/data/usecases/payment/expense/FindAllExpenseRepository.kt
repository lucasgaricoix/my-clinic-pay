package br.com.myclinicpay.data.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface FindAllExpenseRepository {
    fun findAll(month: Int): List<Expense>
}
