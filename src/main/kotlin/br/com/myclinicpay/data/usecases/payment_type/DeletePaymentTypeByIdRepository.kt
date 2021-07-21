package br.com.myclinicpay.data.usecases.payment_type

interface DeletePaymentTypeByIdRepository {
    fun deleteById(id: String)
}