package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncomeByPatient
import org.springframework.stereotype.Service

@Service
class FindAllIncomeByPatientService(private val repository: FindAllIncomeRepository) : FindAllIncomeByPatient {
    override fun find(month: Int): List<IncomeByPatient> {
        val incomes = repository.findAll(month)
        return incomes.groupBy { it.person.name }.map { IncomeByPatient(it.key, it.value) }
    }
}
