package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.FindAllIncomeByPatientService
import br.com.myclinicpay.data.service.payment.income.FindAllIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.presentation.controller.payment.income.FindAllIncomeByPatientController
import br.com.myclinicpay.presentation.controller.payment.income.FindAllIncomeController

class FindAllIncome {
    fun build(): FindAllIncomeController {
        val repository = FindAllIncomeRepository()
        val service = FindAllIncomeService(repository)
        return FindAllIncomeController(service)
    }

    fun allByPatient(): FindAllIncomeByPatientController {
        val repository = FindAllIncomeRepository()
        val service = FindAllIncomeByPatientService(repository)
        return FindAllIncomeByPatientController(service)
    }
}
