package br.com.myclinicpay.domain.model.person

import java.time.LocalDate

class Person(
    val id: String?,
    val name: String,
    val birthDate: LocalDate,
    val age: Int,
    val responsible: Responsible
)
