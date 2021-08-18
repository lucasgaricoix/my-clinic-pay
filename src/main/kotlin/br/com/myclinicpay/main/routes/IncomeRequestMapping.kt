package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.model.payment.PayIncomeById
import br.com.myclinicpay.presentation.factories.payment.income.*
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/incomes")
class IncomeRequestMapping {
    @PostMapping
    fun create(requestEntity: RequestEntity<Income>): ResponseEntity<Income> {
        return CreateIncome().build().handle(requestEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        return DeleteIncome().build().handle(id)
    }

    @PutMapping
    fun update(requestEntity: RequestEntity<Income>): ResponseEntity<String> {
        return UpdateIncome().build().handle(requestEntity)
    }

    @GetMapping("/{id}")
    fun findById(requestEntity: RequestEntity<String>): ResponseEntity<Income> {
        return FindByIdIncome().build().handle(requestEntity)
    }

    @GetMapping("/search")
    fun findAll(@RequestParam(name = "month") search: Int): ResponseEntity<List<Income>> {
        return FindAllIncome().build().handle(search)
    }

    @GetMapping("/by-patients")
    fun findAllByPatient(@RequestParam(name = "month") month: Int): ResponseEntity<List<IncomeByPatient>> {
        return FindAllIncome().allByPatient().handle(month)
    }

    @PutMapping("/pay")
    fun payById(@RequestBody payIncomeById: PayIncomeById): ResponseEntity<String> {
        return UpdateIncome().buildPayById().handle(payIncomeById.id)
    }
}
