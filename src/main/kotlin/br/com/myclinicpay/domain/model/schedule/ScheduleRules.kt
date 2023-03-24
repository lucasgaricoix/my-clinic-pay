package br.com.myclinicpay.domain.model.schedule

data class ScheduleRules(
    val name: String,
    val intervals: List<Interval>
)

data class Interval(
    val from: String,
    val to: String
)
