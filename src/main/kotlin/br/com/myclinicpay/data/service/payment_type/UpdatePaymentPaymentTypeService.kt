package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.UpdatePaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.UpdatePaymentType
import org.springframework.stereotype.Service

@Service
class UpdatePaymentPaymentTypeService(private val repository: UpdatePaymentTypeRepository) : UpdatePaymentType {
    override fun updateById(paymentType: PaymentType): String {
        return repository.updateById(paymentType)
    }
}
