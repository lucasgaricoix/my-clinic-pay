package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.usecases.payment.income.PayIncome
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class PayIncomeController(private val service: PayIncome) {
    fun handle(id: String): ResponseEntity<String> {
        return ResponseEntity(service.pay(id), HttpStatus.OK)
    }
}
