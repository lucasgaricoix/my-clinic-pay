package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.FindByIdIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.FindByIdIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.FindByIdIncomeController

class FindByIdIncome {
    fun build(): FindByIdIncomeController {
        val repository = FindByIdIncomeRepository()
        val service = FindByIdIncomeService(repository)
        return FindByIdIncomeController(service)
    }
}
