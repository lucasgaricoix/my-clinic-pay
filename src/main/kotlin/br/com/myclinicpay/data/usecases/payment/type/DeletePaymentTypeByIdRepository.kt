package br.com.myclinicpay.data.usecases.payment.type

interface DeletePaymentTypeByIdRepository {
    fun deleteById(id: String)
}
