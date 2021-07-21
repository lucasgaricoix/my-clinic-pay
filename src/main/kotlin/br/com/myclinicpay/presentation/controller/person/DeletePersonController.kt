package br.com.myclinicpay.presentation.controller.person

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.DeletePerson
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class DeletePersonController(private val service: DeletePerson) : ControllerInterface<Person, Person> {
    override fun handle(request: RequestEntity<Person>): ResponseEntity<Person> {
        val id = request.url.path.split("/").last()
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}
