package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.DeleteIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.DeleteIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.DeleteIncomeController

class DeleteIncome {
    fun build(): DeleteIncomeController {
        val repository = DeleteIncomeRepository()
        val service = DeleteIncomeService(repository)
        return DeleteIncomeController(service)
    }
}
