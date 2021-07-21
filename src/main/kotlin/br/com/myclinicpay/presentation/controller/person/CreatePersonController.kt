package br.com.myclinicpay.presentation.controller.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.CreatePerson
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class CreatePersonController(private val service: CreatePerson) : ControllerInterface<Person, Person> {
    override fun handle(request: RequestEntity<Person>): ResponseEntity<Person> {
        val created = service.create(request.body)
        return ResponseEntity(created, HttpStatus.CREATED)
    }
}
