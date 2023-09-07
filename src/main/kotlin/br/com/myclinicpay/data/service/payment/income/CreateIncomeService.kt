package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.IncomeRepository
import br.com.myclinicpay.data.usecases.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.CreateIncome
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreateIncomeService(
    private val incomeRepository: IncomeRepository,
    private val findAllBySessionIdIncomeRepository: FindAllBySessionIdIncomeRepository
) : CreateIncome {
    private val lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    private val initialRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1)
    private val finalRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, lastDayMonth)
    override fun create(income: Income): Income {
        val nextSession = this.findLastSessionId()

        if (income.sessionNumber == null) {
            return incomeRepository.create(income, nextSession)
        }

        if (income.person.name.isBlank()) {
            throw Exception("Person not found.")
        }

        return incomeRepository.create(income, nextSession)
    }

    override fun findLastSessionId(): Int {
        return findAllBySessionIdIncomeRepository.findAll(initialRange, finalRange)
    }
}
