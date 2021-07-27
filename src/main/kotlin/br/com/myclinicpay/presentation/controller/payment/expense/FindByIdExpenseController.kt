package br.com.myclinicpay.presentation.controller.payment.expense

import br.com.myclinicpay.data.service.payment.expense.FindByIdExpenseService
import br.com.myclinicpay.domain.model.payment.Expense
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindByIdExpenseController(private val service: FindByIdExpenseService) {
    fun handle(id: String): ResponseEntity<Expense> {
        return ResponseEntity(service.findById(id), HttpStatus.OK)
    }
}
