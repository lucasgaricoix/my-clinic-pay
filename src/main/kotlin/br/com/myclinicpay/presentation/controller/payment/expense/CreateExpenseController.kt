package br.com.myclinicpay.presentation.controller.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.CreateExpense
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class CreateExpenseController(private val service: CreateExpense) {
    fun handle(expense: Expense): ResponseEntity<Expense> {
        return ResponseEntity(service.create(expense), HttpStatus.CREATED)
    }
}
