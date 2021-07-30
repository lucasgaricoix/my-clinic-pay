package br.com.myclinicpay.presentation.controller.payment

import br.com.myclinicpay.domain.model.payment.PaymentOverMonth
import br.com.myclinicpay.domain.usecases.payment.FindAllPaymentOverMonth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class PaymentController(private val findAllPaymentOverMonth: FindAllPaymentOverMonth) {
    fun findAllOverMonth(): ResponseEntity<List<PaymentOverMonth>> {
        return ResponseEntity(findAllPaymentOverMonth.findAllOverMonth(), HttpStatus.OK)
    }
}
