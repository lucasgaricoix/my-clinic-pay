package br.com.myclinicpay.presentation.controller.payment.expense

import br.com.myclinicpay.domain.usecases.payment.expense.DeleteExpense
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class DeleteExpenseController(private val service: DeleteExpense) {
    fun handle(id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
