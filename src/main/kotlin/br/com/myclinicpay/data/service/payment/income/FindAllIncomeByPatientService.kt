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
        val incomes = repository.findAll(month, year).map { toDomainModel(it) }
        return incomes.groupBy { it.person.name }.map { IncomeByPatient(it.key, it.value) }
    }

    private fun toDomainModel(incomeEntity: IncomeEntity): Income {
        return Income(
            incomeEntity.id.toString(),
            incomeEntity.date,
            PaymentType(
                incomeEntity.paymentType.id.toString(),
                TypeEnum.valueOf(incomeEntity.paymentType.type.uppercase()),
                incomeEntity.paymentType.description,
                incomeEntity.paymentType.value
            ),
            incomeEntity.description,
            incomeEntity.sessionNumber,
            incomeEntity.isPaid,
            incomeEntity.isPartial,
            incomeEntity.isAbsence,
            Person(
                incomeEntity.person.id.toString(),
                incomeEntity.person.name,
                incomeEntity.person.birthDate,
                Responsible(
                    incomeEntity.person.responsible.id.toString(),
                    incomeEntity.person.responsible.name
                ),
                incomeEntity.person.paymentType?.let {
                    PaymentType(
                        it.id.toString(),
                        TypeEnum.valueOf(it.type.uppercase()),
                        it.description,
                        it.value
                    )
                },
            )
        )
    }
}
