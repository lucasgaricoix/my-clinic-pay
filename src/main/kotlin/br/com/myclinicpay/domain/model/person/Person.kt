package br.com.myclinicpay.domain.model.person

import java.time.LocalDateTime

class Person(
    var name: String,
    var birthDate: LocalDateTime,
    var age: Int,
    val responsible: Responsible
)