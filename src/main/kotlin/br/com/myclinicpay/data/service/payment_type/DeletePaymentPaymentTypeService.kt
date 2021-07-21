package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.domain.usecases.payment.DeletePaymentTypeById
import org.springframework.stereotype.Service

@Service
class DeletePaymentPaymentTypeService(private val repositoryPayment: DeletePaymentTypeByIdRepository) : DeletePaymentTypeById {
    override fun deleteById(id: String) {
        repositoryPayment.deleteById(id)
    }
}
