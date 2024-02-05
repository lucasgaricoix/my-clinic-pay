package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment.IncomeByPatient
import br.com.myclinicpay.domain.model.payment.PayIncomeById
import br.com.myclinicpay.presentation.controller.payment.income.IncomeController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/incomes")
class IncomeRequestMapping @Autowired constructor(private val incomeController: IncomeController) {
    @PostMapping
    fun create(requestEntity: RequestEntity<Income>): ResponseEntity<Income> {
        return incomeController.create(requestEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        return incomeController.delete(id)
    }

    @PutMapping
    fun update(requestEntity: RequestEntity<Income>): ResponseEntity<String> {
        return incomeController.update(requestEntity)
    }

    @GetMapping("/{id}")
    fun findById(requestEntity: RequestEntity<String>): ResponseEntity<Income> {
        return incomeController.findById(requestEntity)
    }

    @GetMapping("/search")
    fun findAll(
        @RequestParam(name = "month") month: Int,
        @RequestParam(name = "year") year: Int
    ): ResponseEntity<List<Income>> {
        return incomeController.findAll(month, year)
    }

    @GetMapping("/by-patients")
    fun findAllByPatient(
        @RequestParam(name = "month") month: Int,
        @RequestParam(name = "year") year: Int
    ): ResponseEntity<List<IncomeByPatient>> {
        return incomeController.findByMonthAndYear(month, year)
    }

    @PutMapping("/pay")
    fun payById(@RequestBody payIncomeById: PayIncomeById): ResponseEntity<String> {
        return incomeController.pay(payIncomeById.id)
    }
}
