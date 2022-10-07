package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncome
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllIncomeController(private val service: FindAllIncome) {
    fun handle(month: Int, year: Int): ResponseEntity<List<Income>> {
        return ResponseEntity(service.findAll(month, year), HttpStatus.OK)
    }
}
