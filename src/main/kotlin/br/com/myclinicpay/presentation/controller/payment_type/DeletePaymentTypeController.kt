package br.com.myclinicpay.presentation.controller.payment_type

import br.com.myclinicpay.data.service.payment_type.DeletePaymentTypeService
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class DeletePaymentTypeController(private val service: DeletePaymentTypeService) : ControllerInterface<PaymentType, PaymentType> {
    override fun handle(request: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        val id = request.url.path.split("/").last()
        service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
