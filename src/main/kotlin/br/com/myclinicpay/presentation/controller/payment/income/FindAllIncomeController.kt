package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncome
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllIncomeController(private val service: FindAllIncome) {
    fun handle(search: Int): ResponseEntity<List<Income>> {
        return ResponseEntity(service.findAll(search), HttpStatus.OK)
    }
}
