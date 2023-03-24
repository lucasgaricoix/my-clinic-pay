package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.domain.usecases.payment.income.FindAllIncome
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.stereotype.Service

@Service
class FindAllIncomeService(private val repository: FindAllIncomeRepository) : FindAllIncome {
    override fun findAll(month: Int, year: Int): List<Income> {
        return repository.findAll(month, year).map { toDomainModel(it) }
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
                }
            )
        )
    }
}
