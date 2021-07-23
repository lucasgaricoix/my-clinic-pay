package br.com.myclinicpay.presentation.controller.payment_type

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment_type.FindAllPaymentTypeByType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllPaymentTypeByTypeController(private val service: FindAllPaymentTypeByType) :
    ControllerInterface<PaymentType, List<PaymentType>> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<List<PaymentType>> {
        val query = request.url.query
        return ResponseEntity(service.findAllByType(query), HttpStatus.OK)
    }
}
