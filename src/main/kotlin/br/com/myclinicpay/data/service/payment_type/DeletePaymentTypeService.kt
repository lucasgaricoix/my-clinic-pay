package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.domain.usecases.payment.DeleteTypeValueById
import org.springframework.stereotype.Service

@Service
class DeletePaymentTypeService(private val repositoryPayment: DeletePaymentTypeByIdRepository) : DeleteTypeValueById {
    override fun deleteById(id: String) {
        repositoryPayment.deleteById(id)
    }
}