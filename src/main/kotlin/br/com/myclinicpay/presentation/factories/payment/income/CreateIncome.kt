package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.CreateIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.CreateIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.CreateIncomeController

class CreateIncome {
    fun build(): CreateIncomeController {
        val createIncomeRepository = CreateIncomeRepository()
        val findAllBySessionIdIncomeRepository = FindAllBySessionIdIncomeRepository()
        val service = CreateIncomeService(
            createIncomeRepository, findAllBySessionIdIncomeRepository
        )
        return CreateIncomeController(service)
    }
}
