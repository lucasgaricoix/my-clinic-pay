package br.com.myclinicpay.presentation.controller.payment_type

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.FindPaymentTypeById
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class FindPaymentTypeByIdController(private val service: FindPaymentTypeById) : ControllerInterface<PaymentType> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        val id = request.url.path.split("/")[2]
        return ResponseEntity(service.findById(id), HttpStatus.OK)
    }
}
