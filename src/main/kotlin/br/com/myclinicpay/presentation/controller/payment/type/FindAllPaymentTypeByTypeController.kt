package br.com.myclinicpay.presentation.controller.payment.type

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.type.FindAllPaymentTypeByType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllPaymentTypeByTypeController(private val service: FindAllPaymentTypeByType) {
    fun handle(description: String, type: String): ResponseEntity<List<PaymentType>> {
        return ResponseEntity(service.findAllByType(description, type), HttpStatus.OK)
    }
}
