package br.com.myclinicpay.data.usecases.person

interface DeletePersonRepository {
    fun deleteById(id: String)
}
