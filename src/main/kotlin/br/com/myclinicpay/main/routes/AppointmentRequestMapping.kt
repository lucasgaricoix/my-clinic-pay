package br.com.myclinicpay.main.routes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appointment")
class AppointmentRequestMapping {

    @PostMapping
    fun createAppointment() {

    }

    @GetMapping
    fun findAppointment() {

    }
}
