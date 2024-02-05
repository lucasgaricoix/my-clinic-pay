package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.IncomeRepository
import br.com.myclinicpay.domain.usecases.payment.income.DeleteIncome
import org.springframework.stereotype.Service

@Service
class DeleteIncomeService(private val repository: IncomeRepository) : DeleteIncome {
    override fun delete(id: String) = repository.deleteById(id)

    override fun deleteByScheduleId(scheduleId: String) = repository.deleteByScheduleId(scheduleId)
}
