package br.com.myclinicpay.presentation.controller.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindAllPerson
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class FindAllPersonController(private val service: FindAllPerson) : ControllerInterface<Person, List<Person>> {
    override fun handle(request: RequestEntity<Person>): ResponseEntity<List<Person>> {
        val person = service.findAll()
        return ResponseEntity(person, HttpStatus.OK)
    }
}
