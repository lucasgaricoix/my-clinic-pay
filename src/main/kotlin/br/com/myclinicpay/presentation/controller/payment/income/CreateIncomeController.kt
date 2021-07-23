package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.CreateIncome
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class CreateIncomeController(private val service: CreateIncome) :
    ControllerInterface<Income, Income> {
    override fun handle(request: RequestEntity<Income>): ResponseEntity<Income> {
        val body = request.body ?: throw Exception("Empty body")
        return ResponseEntity(service.create(body), HttpStatus.CREATED)
    }
}
