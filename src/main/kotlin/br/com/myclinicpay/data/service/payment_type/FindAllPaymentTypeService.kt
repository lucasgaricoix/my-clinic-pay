package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.FindAllPaymentTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment_type.FindAllPaymentType
import org.springframework.stereotype.Service

@Service
class FindAllPaymentTypeService(private val repositoryPayment: FindAllPaymentTypeRepository) : FindAllPaymentType {
    override fun findAll(): List<PaymentType> {
        return repositoryPayment.findAllPaymentType()
    }
}
