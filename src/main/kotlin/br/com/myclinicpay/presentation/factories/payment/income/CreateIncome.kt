package br.com.myclinicpay.presentation.factories.payment.income

import br.com.myclinicpay.data.service.payment.income.CreateIncomeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.CreateIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
import br.com.myclinicpay.presentation.controller.payment.income.CreateIncomeController

class CreateIncome {
    fun build(): CreateIncomeController {
        val createIncomeRepository = CreateIncomeRepository()
        val findPaymentTypeByIdRepository = FindPaymentTypeByIdRepository()
        val findPersonByIdRepository = FindPersonByIdRepository()
        val findAllBySessionIdIncomeRepository = FindAllBySessionIdIncomeRepository()
        val service =
            CreateIncomeService(
                createIncomeRepository,
                findPaymentTypeByIdRepository,
                findPersonByIdRepository,
                findAllBySessionIdIncomeRepository
            )
        return CreateIncomeController(service)
    }
}
