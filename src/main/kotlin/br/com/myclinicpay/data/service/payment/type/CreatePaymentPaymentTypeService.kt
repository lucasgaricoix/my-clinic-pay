package br.com.myclinicpay.data.service.payment.type

import br.com.myclinicpay.data.usecases.payment.type.CreatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.type.CreatePaymentType
import org.springframework.stereotype.Service

@Service
class CreatePaymentPaymentTypeService(private val repositoryPayment: CreatePaymentTypeRepository) : CreatePaymentType {
    override fun create(paymentType: PaymentType): PaymentType {
        return repositoryPayment.save(paymentType)
    }
}
