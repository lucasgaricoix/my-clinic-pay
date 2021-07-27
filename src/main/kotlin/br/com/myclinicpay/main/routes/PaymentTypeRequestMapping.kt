package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.presentation.factories.payment.type.*
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payments/types")
class PaymentTypeRequestMapping {

    @PostMapping
    fun create(requestEntity: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        return CreatePaymentType().build().handle(requestEntity)
    }

    @PutMapping
    fun update(requestEntity: RequestEntity<PaymentType>): ResponseEntity<String> {
        return UpdatePaymentType().build().handle(requestEntity)
    }

    @GetMapping
    fun findAll(requestEntity: RequestEntity<PaymentType>): ResponseEntity<List<PaymentType>> {
        return FindAllPaymentType().build().handle(requestEntity)
    }

    @GetMapping("/search")
    fun findAllByType(
        @RequestParam(name = "description") description: String,
        @RequestParam(name = "type") type: String
    ): ResponseEntity<List<PaymentType>> {
        return FindAllPaymentType().buildByType().handle(description, type)
    }

    @GetMapping("/{id}")
    fun findById(requestEntity: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        return FindPaymentTypeById().build().handle(requestEntity)
    }

    @DeleteMapping("/{id}")
    fun deleteById(requestEntity: RequestEntity<PaymentType>): ResponseEntity<PaymentType> {
        return DeletePaymentType().build().handle(requestEntity)
    }
}
