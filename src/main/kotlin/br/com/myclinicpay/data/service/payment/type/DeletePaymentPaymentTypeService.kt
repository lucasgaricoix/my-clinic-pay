package br.com.myclinicpay.data.service.payment.type

import br.com.myclinicpay.data.usecases.payment.type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.domain.usecases.payment.type.DeletePaymentTypeById
import org.springframework.stereotype.Service

@Service
class DeletePaymentPaymentTypeService(private val repositoryPayment: DeletePaymentTypeByIdRepository) :
    DeletePaymentTypeById {
    override fun deleteById(id: String) {
        repositoryPayment.deleteById(id)
    }
}
