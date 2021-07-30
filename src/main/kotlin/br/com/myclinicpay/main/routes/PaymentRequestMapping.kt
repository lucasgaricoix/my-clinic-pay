package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment.PaymentOverMonth
import br.com.myclinicpay.presentation.factories.payment.PaymentFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentRequestMapping {

    @GetMapping("/all/over-month")
    fun findAllOverMonth(): ResponseEntity<List<PaymentOverMonth>> {
        return PaymentFactory().build().findAllOverMonth()
    }
}
