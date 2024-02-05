package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.usecases.payment.income.FindIncome
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class FindIncomeService(private val repository: FindIncomeRepository) : FindIncome {
    override fun findById(id: String): Income = repository.findById(id).toDomainModel()

    override fun findAll(month: Int, year: Int): List<Income> =
        repository.findAll(month, year).map { it.toDomainModel() }

    override fun findByMonthAndYear(month: Int, year: Int): List<IncomeByPatient> {
        val incomes = repository.findAll(month, year).map { it.toDomainModel() }
        return incomes.groupBy { it.person.name }.map { IncomeByPatient(it.key, it.value) }
    }

    override fun findLastSessionByDateRange(initialRange: LocalDate, finalRange: LocalDate): Int =
        repository.findLastSession(initialRange, finalRange)
}
