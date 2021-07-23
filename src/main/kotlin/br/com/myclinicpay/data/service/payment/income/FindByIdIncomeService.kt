package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindByIdIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.FindByIdIncome
import org.springframework.stereotype.Service

@Service
class FindByIdIncomeService(private val repository: FindByIdIncomeRepository) : FindByIdIncome {
    override fun findById(id: String): Income {
        return repository.findById(id)
    }
}
