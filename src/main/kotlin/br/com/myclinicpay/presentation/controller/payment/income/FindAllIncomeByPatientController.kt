package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncomeByPatient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllIncomeByPatientController(private val service: FindAllIncomeByPatient) {
    fun handle(month: Int): ResponseEntity<List<IncomeByPatient>> {
        return ResponseEntity(service.find(month), HttpStatus.OK)
    }
}
