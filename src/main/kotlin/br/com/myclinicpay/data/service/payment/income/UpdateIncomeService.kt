package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.UpdateIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.UpdateIncome

class UpdateIncomeService(private val repository: UpdateIncomeRepository): UpdateIncome {
    override fun udpateById(income: Income): String {
        return repository.update(income)
    }
}
