package br.com.myclinicpay.infra.db.mongoDb.entities

import java.time.Month

data class PaymentOverMonthData(
    val type: String,
    val year: Int,
    val month: Month,
    val value: Double
)
