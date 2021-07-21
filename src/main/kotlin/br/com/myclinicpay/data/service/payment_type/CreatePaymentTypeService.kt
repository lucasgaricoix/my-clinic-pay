package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.CreatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.CreateTypeValue
import org.springframework.stereotype.Service

@Service
class CreatePaymentTypeService(private val repositoryPayment: CreatePaymentTypeRepository) : CreateTypeValue {
    override fun create(paymentType: PaymentType): PaymentType {
        return repositoryPayment.save(paymentType)
    }
}