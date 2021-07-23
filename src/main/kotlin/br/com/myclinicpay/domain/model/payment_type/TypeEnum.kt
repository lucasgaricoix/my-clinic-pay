package br.com.myclinicpay.domain.model.payment_type

import lombok.Data

@Data
enum class TypeEnum(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}
