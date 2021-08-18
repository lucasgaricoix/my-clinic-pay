package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.PayIncomeService
import br.com.myclinicpay.data.service.payment.income.UpdateIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.PayIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.UpdateIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.PayIncomeController
import br.com.myclinicpay.presentation.controller.payment.income.UpdateIncomeController

class UpdateIncome {
    fun build(): UpdateIncomeController {
        val repository = UpdateIncomeRepository()
        val service = UpdateIncomeService(repository)
        return UpdateIncomeController(service)
    }

    fun buildPayById(): PayIncomeController {
        val repository = PayIncomeRepository()
        val service = PayIncomeService(repository)
        return PayIncomeController(service)
    }
}
