package br.com.myclinicpay.data.service.payment

import br.com.myclinicpay.data.usecases.payment.FindAllPaymentOverMonthRepository
import br.com.myclinicpay.domain.model.payment.PaymentOverMonth
import br.com.myclinicpay.domain.usecases.payment.FindAllPaymentOverMonth
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentOverMonthData
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class FindAllPaymentOverMonthService(
    private val repository: FindAllPaymentOverMonthRepository
) : FindAllPaymentOverMonth {
    override fun findAllOverMonth(): List<PaymentOverMonth> {
        val finalDate = LocalDate.now()
        val initialDate = finalDate.minusMonths(6L)
        val payments = repository.findAllOverMonth(initialDate, finalDate)
        val paymentOverMonthList: MutableList<PaymentOverMonth> = ArrayList()

        payments.map { PaymentOverMonthData(it.paymentType.type, it.date.year, it.date.month, it.paymentType.value) }
            .sortedBy { it.year; it.month }
            .forEach { payment ->
                if (payment.type == "income") {
                    paymentOverMonthList.add(PaymentOverMonth(payment.year, payment.month.name, payment.value, 0.0, 0.0))
                } else {
                    paymentOverMonthList.add(PaymentOverMonth(payment.year, payment.month.name, 0.0, payment.value, 0.0))
                }
            }

        val result = paymentOverMonthList.groupingBy { it.year; it.month }.reduce { key, acc, element ->
            val income = acc.income.plus(element.income)
            val expense = acc.expense.plus(element.expense)
            val total = acc.income.plus(element.income).plus(acc.expense.plus(element.expense))
            PaymentOverMonth(acc.year, key, income, expense, total)
        }

        return result.map {
            PaymentOverMonth(
                it.value.year,
                it.value.month,
                it.value.income,
                it.value.expense,
                it.value.amount
            )
        }.sortedBy { it.year; it.month }
    }
}
