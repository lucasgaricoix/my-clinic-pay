package br.com.myclinicpay.presentation.controller.payment_type

import br.com.myclinicpay.data.service.payment_type.FindAllPaymentTypeService
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllPaymentTypeController(private val service: FindAllPaymentTypeService) :
    ControllerInterface<PaymentType, List<PaymentType>> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<List<PaymentType>> {
        return ResponseEntity(service.findAll(), HttpStatus.OK)
    }
}
