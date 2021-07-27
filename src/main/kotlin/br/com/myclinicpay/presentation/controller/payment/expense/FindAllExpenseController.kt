package br.com.myclinicpay.presentation.controller.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.FindAllExpense
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllExpenseController(private val service: FindAllExpense) {
    fun handle(): ResponseEntity<List<Expense>> {
        return ResponseEntity(service.findAll(), HttpStatus.OK)
    }
}
