package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.presentation.factories.payment.expense.ExpenseBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpenseRequestMapping {
    @PostMapping
    fun create(@RequestBody body: Expense) : ResponseEntity<Expense>{
        return ExpenseBuilder().create().handle(body)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        return ExpenseBuilder().delete().handle(id)
    }

    @GetMapping
    fun findAll(@RequestParam(name = "month") month: Int): ResponseEntity<List<Expense>> {
        return ExpenseBuilder().findAll().handle(month)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<Expense> {
        return ExpenseBuilder().findById().handle(id)
    }

    @PutMapping
    fun update(@RequestBody expense: Expense): ResponseEntity<String> {
        return ExpenseBuilder().update().handle(expense)
    }
}
