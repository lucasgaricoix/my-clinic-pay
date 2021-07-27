package br.com.myclinicpay.data.service.payment.type

import br.com.myclinicpay.data.usecases.payment.type.FindAllPaymentTypeByTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.type.FindAllPaymentTypeByType
import org.springframework.stereotype.Service

@Service
class FindAllPaymentTypeByTypeService(private val repository: FindAllPaymentTypeByTypeRepository) :
    FindAllPaymentTypeByType {
    override fun findAllByType(description: String, type: String): List<PaymentType> {
        return repository.findAllByType(description, type)
    }
}
