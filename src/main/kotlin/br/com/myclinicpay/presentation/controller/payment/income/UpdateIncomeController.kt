package br.com.myclinicpay.presentation.controller.payment.income

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.income.UpdateIncome
import br.com.myclinicpay.presentation.usecases.ControllerInterface
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.client.HttpServerErrorException

@Controller
class UpdateIncomeController(private val service: UpdateIncome) : ControllerInterface<Income, String> {
    override fun handle(request: RequestEntity<Income>): ResponseEntity<String> {
        val body = request.body ?: throw HttpServerErrorException(HttpStatus.NO_CONTENT, "Empty body.")
        return ResponseEntity(service.update(body), HttpStatus.OK)
    }
}
