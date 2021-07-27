package br.com.myclinicpay.presentation.controller.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.UpdateExpense
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class UpdateExpenseController(private val service: UpdateExpense) {
    fun handle(expense: Expense): ResponseEntity<String> {
        return ResponseEntity(service.update(expense), HttpStatus.OK)
    }
}
