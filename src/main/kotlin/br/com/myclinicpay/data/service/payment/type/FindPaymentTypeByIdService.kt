package br.com.myclinicpay.data.service.payment.type

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.type.FindPaymentTypeById
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

@Service
class FindPaymentTypeByIdService(private val repository: FindPaymentTypeByIdRepository) : FindPaymentTypeById {
    override fun findById(id: String): PaymentType {
        val paymentType = repository.findById(id) ?: throw HttpServerErrorException(
            HttpStatus.NOT_FOUND,
            "Não foi encontrado o Tipo de pagamento."
        )
        return paymentType.toModel()
    }
}
