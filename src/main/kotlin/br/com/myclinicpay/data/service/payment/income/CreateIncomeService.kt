package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.CreateIncomeRepository
import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.CreateIncome
import org.springframework.stereotype.Service

@Service
class CreateIncomeService(
    private val createIncomeRepository: CreateIncomeRepository,
    private val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository,
    private val findPersonByIdRepository: FindPersonByIdRepository
) : CreateIncome {
    override fun create(income: Income): Income {
        val paymentType = findPaymentTypeByIdRepository.findById(income.paymentType.id.orEmpty())
        val person = findPersonByIdRepository.findById(income.person.id.orEmpty())
        return createIncomeRepository.create(income, paymentType, person)
    }
}
