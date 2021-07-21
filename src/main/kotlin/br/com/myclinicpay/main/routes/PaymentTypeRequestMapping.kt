package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.presentation.factories.payment_type.CreatePaymentType
import br.com.myclinicpay.presentation.factories.payment_type.DeletePaymentType
import br.com.myclinicpay.presentation.factories.payment_type.FindAllPaymentType
import br.com.myclinicpay.presentation.factories.payment_type.FindPaymentTypeById
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment/type")
class PaymentTypeRequestMapping {

    @PostMapping
    fun create(requestEntity: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        return CreatePaymentType().build().handle(requestEntity)
    }

    @GetMapping
    fun findAll(requestEntity: RequestEntity<PaymentType>) : ResponseEntity<List<PaymentType>> {
        return FindAllPaymentType().build().handleList(requestEntity)
    }

    @GetMapping("/{id}")
    fun findById(requestEntity: RequestEntity<PaymentType>) : ResponseEntity<PaymentType> {
        return FindPaymentTypeById().build().handle(requestEntity)
    }

    @DeleteMapping
    fun deleteById(requestEntity: RequestEntity<PaymentType>) : ResponseEntity<PaymentType> {
        return DeletePaymentType().build().handle(requestEntity)
    }
}
