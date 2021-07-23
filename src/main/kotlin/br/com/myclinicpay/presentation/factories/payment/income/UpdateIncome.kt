package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.UpdateIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.UpdateIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.UpdateIncomeController

class UpdateIncome {
    fun build(): UpdateIncomeController {
        val repository = UpdateIncomeRepository()
        val service = UpdateIncomeService(repository)
        return UpdateIncomeController(service)
    }
}
