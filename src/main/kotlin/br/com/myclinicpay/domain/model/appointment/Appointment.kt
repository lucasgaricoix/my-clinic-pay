package br.com.myclinicpay.domain.model.appointment

import br.com.myclinicpay.domain.model.person.Person
import java.time.LocalDate

data class Appointment(
    val patient: Person,
    val duration: Int,
    val at: LocalDate,
    val description: String?
)
