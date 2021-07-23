package br.com.myclinicpay.infra.db.mongoDb.repository.payment_type

import br.com.myclinicpay.data.usecases.payment_type.FindAllPaymentTypeByTypeRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class FindAllPaymentTypesByTypeRepository : FindAllPaymentTypeByTypeRepository {
    private val collectionName = "payment_type"
    override fun findAllByType(query: String): List<PaymentType> {
        val mongoTemplate = Connection.getTemplate()
        val documentQuery = Query(Criteria.where("type").isEqualTo(query))

        return mongoTemplate.find(documentQuery, PaymentTypeEntity::class.java, collectionName)
            .map { adapter(it) }
            .toList()
    }

    private fun adapter(paymentTypeEntity: PaymentTypeEntity): PaymentType {
        return PaymentType(
            paymentTypeEntity.id.toString(),
            TypeEnum.valueOf(paymentTypeEntity.type.uppercase()),
            paymentTypeEntity.description,
            paymentTypeEntity.value
        )
    }
}
