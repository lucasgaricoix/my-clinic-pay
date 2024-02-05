package br.com.myclinicpay.domain.usecases.payment.income

interface DeleteIncome {
    fun delete(id: String)

    fun deleteByScheduleId(scheduleId: String): String
}
