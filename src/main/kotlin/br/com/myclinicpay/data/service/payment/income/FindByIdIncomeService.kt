package br.com.myclinicpay.data.service.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindByIdIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.domain.usecases.payment.income.FindByIdIncome
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.stereotype.Service

@Service
class FindByIdIncomeService(private val repository: FindByIdIncomeRepository) : FindByIdIncome {
    override fun findById(id: String): Income {
        return this.toDomainModel(repository.findById(id))
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
