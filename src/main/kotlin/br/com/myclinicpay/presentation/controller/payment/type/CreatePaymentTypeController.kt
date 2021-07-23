package br.com.myclinicpay.presentation.controller.payment.type

import br.com.myclinicpay.data.service.payment.type.CreatePaymentPaymentTypeService
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class CreatePaymentTypeController(private val servicePayment: CreatePaymentPaymentTypeService) :
    ControllerInterface<PaymentType, PaymentType> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        val body = request.body ?: throw Exception("Empty body")

        return ResponseEntity(servicePayment.create(body), HttpStatus.CREATED)
    }
}
