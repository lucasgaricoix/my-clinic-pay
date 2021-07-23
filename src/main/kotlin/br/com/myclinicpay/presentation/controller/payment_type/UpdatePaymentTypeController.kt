package br.com.myclinicpay.presentation.controller.payment_type

import br.com.myclinicpay.data.service.payment_type.CreatePaymentPaymentTypeService
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.UpdatePaymentType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class UpdatePaymentTypeController(private val service: UpdatePaymentType) :
    ControllerInterface<PaymentType, String> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<String> {
        val body = request.body ?: throw Exception("Empty body")

        return ResponseEntity(service.updateById(body), HttpStatus.CREATED)
    }
}
