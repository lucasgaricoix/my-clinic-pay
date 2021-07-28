package br.com.myclinicpay.presentation.controller.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.UpdatePerson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class UpdatePersonController(private val service: UpdatePerson) {
    fun handle(person: Person): ResponseEntity<String> {
        return ResponseEntity(service.update(person), HttpStatus.OK)
    }
}
