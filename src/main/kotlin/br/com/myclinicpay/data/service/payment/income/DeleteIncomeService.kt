package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.DeleteIncomeRepository
import br.com.myclinicpay.domain.usecases.payment.income.DeleteIncome

class DeleteIncomeService(private val repository: DeleteIncomeRepository) : DeleteIncome {
    override fun delete(id: String) {
        return repository.deleteById(id)
    }
}
