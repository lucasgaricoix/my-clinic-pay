package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.presentation.factories.payment.income.CreateIncome
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/income")
class IncomeRequestMapping {
    @PostMapping
    fun create(requestEntity: RequestEntity<Income>) : ResponseEntity<Income> {
        return CreateIncome().build().handle(requestEntity)
    }
}
