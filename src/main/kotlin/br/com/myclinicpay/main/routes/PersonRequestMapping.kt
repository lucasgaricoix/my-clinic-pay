package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.presentation.factories.person.*
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("persons")
class PersonRequestMapping {
    @PostMapping
    fun create(requestEntity: RequestEntity<Person>): ResponseEntity<Person> {
        return CreatePerson().build().handle(requestEntity)
    }

    @DeleteMapping("/{id}")
    fun deleteById(requestEntity: RequestEntity<Person>) : ResponseEntity<Person> {
        return DeletePerson().build().handle(requestEntity)
    }

    @PutMapping
    fun update(@RequestBody person: Person): ResponseEntity<String> {
        return UpdatePerson().build().handle(person)
    }

    @GetMapping
    fun findAll(requestEntity: RequestEntity<Person>) : ResponseEntity<List<Person>> {
        return FindAllPerson().build().handle(requestEntity)
    }

    @GetMapping("/{id}")
    fun findById(requestEntity: RequestEntity<Person>) : ResponseEntity<Person> {
        return FindPersonById().build().handle(requestEntity)
    }

    @GetMapping("/search")
    fun search(@RequestParam(name = "name") search: String): ResponseEntity<List<Person>> {
        return SearchPerson().build().handle(search)
    }
}
