package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.PayIncomeRepository
import br.com.myclinicpay.domain.usecases.payment.income.PayIncome
import org.springframework.stereotype.Service

@Service
class PayIncomeService(private val repository: PayIncomeRepository) : PayIncome {
    override fun pay(id: String): String {
        return repository.pay(id)
    }
}
