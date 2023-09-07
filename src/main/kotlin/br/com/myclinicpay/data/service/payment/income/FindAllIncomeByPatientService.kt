package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncomeByPatient
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.stereotype.Service

@Service
class FindAllIncomeByPatientService(private val repository: FindAllIncomeRepository) : FindAllIncomeByPatient {
    override fun find(month: Int, year: Int): List<IncomeByPatient> {
        val incomes = repository.findAll(month, year).map { it.toDomainModel() }
        return incomes.groupBy { it.person.name }.map { IncomeByPatient(it.key, it.value) }
    }
}
