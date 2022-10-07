package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.CreateIncomeRepository
import br.com.myclinicpay.data.usecases.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.CreateIncome
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreateIncomeService(
    private val createExpenseRepository: CreateIncomeRepository,
    private val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository,
    private val findPersonByIdRepository: FindPersonByIdRepository,
    private val findAllBySessionIdIncomeRepository: FindAllBySessionIdIncomeRepository
) : CreateIncome {
    override fun create(income: Income): Income {
        val paymentType = findPaymentTypeByIdRepository.findById(income.paymentType.id.orEmpty())
        val person = findPersonByIdRepository.findById(income.person.id.orEmpty())
        val lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        val initialRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1)
        val finalRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, lastDayMonth)
        val nextSession = findAllBySessionIdIncomeRepository.findAll(initialRange, finalRange)

        if (income.sessionNumber == null) {
            return createExpenseRepository.create(income, paymentType, person, nextSession)
        }

        if (income.person.name.isBlank()) {
            throw Exception("Person not found.")
        }

        return createExpenseRepository.create(income, paymentType, person, income.sessionNumber)
    }
}
