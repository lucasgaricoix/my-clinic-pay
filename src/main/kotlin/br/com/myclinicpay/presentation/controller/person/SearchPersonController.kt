package br.com.myclinicpay.presentation.controller.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindAllPerson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class SearchPersonController(private val service: FindAllPerson){
    fun handle(search: String): ResponseEntity<List<Person>> {
        val person = service.findAll(search)
        return ResponseEntity(person, HttpStatus.OK)
    }
}
