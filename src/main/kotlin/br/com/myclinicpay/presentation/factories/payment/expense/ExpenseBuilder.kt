package br.com.myclinicpay.presentation.factories.payment.expense

import br.com.myclinicpay.data.service.payment.expense.*
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense.*
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.presentation.controller.payment.expense.*

class ExpenseBuilder {
    fun create(): CreateExpenseController {
        val repository = CreateExpenseRepository()
        val paymentTypeRepository = FindPaymentTypeByIdRepository()
        val service = CreateExpenseService(repository, paymentTypeRepository)
        return CreateExpenseController(service)
    }

    fun update(): UpdateExpenseController {
        val repository = UpdateExpenseRepository()
        val service = UpdateExpenseService(repository)
        return UpdateExpenseController(service)
    }

    fun findAll(): FindAllExpenseController {
        val repository = FindAllExpenseRepository()
        val service = FindAllExpenseService(repository)
        return FindAllExpenseController(service)
    }

    fun findById(): FindByIdExpenseController {
        val repository = FindByIdExpenseRepository()
        val service = FindByIdExpenseService(repository)
        return FindByIdExpenseController(service)
    }

    fun delete(): DeleteExpenseController {
        val repository = DeleteExpenseRepository()
        val service = DeleteExpenseService(repository)
        return DeleteExpenseController(service)
    }
}
