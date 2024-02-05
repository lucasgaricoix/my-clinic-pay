package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.usecases.payment.income.*
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.client.HttpServerErrorException

@Controller
class IncomeController(
    private val createIncomeService: CreateIncome,
    private val deleteIncomeService: DeleteIncome,
    private val findIncomeService: FindIncome,
    private val updateIncomeService: UpdateIncome,
    private val payIncomeService: PayIncome
) {
    fun create(request: RequestEntity<Income>): ResponseEntity<Income> {
        val body = request.body ?: throw Exception("Empty body")
        return ResponseEntity(createIncomeService.create(body), HttpStatus.CREATED)
    }

    fun delete(id: String): ResponseEntity<Void> {
        deleteIncomeService.delete(id)
        return ResponseEntity.noContent().build()
    }

    fun findById(request: RequestEntity<String>): ResponseEntity<Income> {
        val id = request.url.path.split("/").last()
        return ResponseEntity(findIncomeService.findById(id), HttpStatus.OK)
    }

    fun findByMonthAndYear(month: Int, year: Int): ResponseEntity<List<IncomeByPatient>> {
        return ResponseEntity(findIncomeService.findByMonthAndYear(month, year), HttpStatus.OK)
    }

    fun findAll(month: Int, year: Int): ResponseEntity<List<Income>> {
        return ResponseEntity(findIncomeService.findAll(month, year), HttpStatus.OK)
    }

    fun pay(id: String): ResponseEntity<String> {
        return ResponseEntity(payIncomeService.pay(id), HttpStatus.OK)
    }

    fun update(request: RequestEntity<Income>): ResponseEntity<String> {
        val body = request.body ?: throw HttpServerErrorException(HttpStatus.NO_CONTENT, "Empty body.")
        return ResponseEntity(updateIncomeService.update(body), HttpStatus.OK)
    }
}
