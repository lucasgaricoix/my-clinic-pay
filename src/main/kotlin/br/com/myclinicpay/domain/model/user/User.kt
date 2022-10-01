package br.com.myclinicpay.domain.model.user

class User(
    val name: String,
    val email: String,
    val picture: String,
    val role: String = "standard",
    val tenantId: String? = "default"
)