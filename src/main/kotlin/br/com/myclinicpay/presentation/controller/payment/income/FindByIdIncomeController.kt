package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.FindByIdIncome
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindByIdIncomeController(private val service: FindByIdIncome) : ControllerInterface<String, Income> {
    override fun handle(request: RequestEntity<String>): ResponseEntity<Income> {
        val id = request.url.path.split("/").last()
        return ResponseEntity(service.findById(id), HttpStatus.OK)
    }
}
