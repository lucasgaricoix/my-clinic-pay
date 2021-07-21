package br.com.myclinicpay.presentation.usecases

import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

interface ControllerInterface<S,T> {
    fun handle(request: RequestEntity<S>): ResponseEntity<T>
}
