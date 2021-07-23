package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.CreatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment_type.CreatePaymentType
import org.springframework.stereotype.Service

@Service
class CreatePaymentPaymentTypeService(private val repositoryPayment: CreatePaymentTypeRepository) : CreatePaymentType {
    override fun create(paymentType: PaymentType): PaymentType {
        return repositoryPayment.save(paymentType)
    }
}
