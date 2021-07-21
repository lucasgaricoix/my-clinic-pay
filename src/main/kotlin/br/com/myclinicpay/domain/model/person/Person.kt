package br.com.myclinicpay.domain.model.person

import java.time.LocalDateTime

class Person(
    val name: String,
    val birthDate: LocalDateTime,
    val age: Int,
    val responsible: Responsible
)
