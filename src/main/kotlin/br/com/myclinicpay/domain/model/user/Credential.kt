package br.com.myclinicpay.domain.model.user

import java.io.Serializable

data class Credential(val username: String = "", val password: String = "") : Serializable
