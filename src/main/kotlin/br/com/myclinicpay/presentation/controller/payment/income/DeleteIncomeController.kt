package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.usecases.payment.income.DeleteIncome
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class DeleteIncomeController(private val service: DeleteIncome) {
    fun handle(id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
