package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncome
import org.springframework.stereotype.Service

@Service
class FindAllIncomeService(private val repository: FindAllIncomeRepository) : FindAllIncome {
    override fun findAll(month: Int, year: Int): List<Income> {
        return repository.findAll(month, year).map { it.toDomainModel() }
    }
}
