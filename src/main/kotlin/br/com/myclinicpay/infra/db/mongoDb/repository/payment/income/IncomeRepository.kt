package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.IncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class IncomeRepository : IncomeRepository {
    private val collectionName = "income"
    override fun create(income: Income, lastSession: Int?): Income {
        try {
            val mongodbTemplate = Connection.getTemplate()
            val incomeEntity = income.toEntity(lastSession)
            val created = mongodbTemplate.save<IncomeEntity>(incomeEntity, collectionName)
            mongodbTemplate.save(toPaymentEntity(created))
            return created.toDomainModel()
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível persistir o pagament\n$error"
            )
        }
    }

    override fun findById(id: String): IncomeEntity {
        val mongodbTemplate = Connection.getTemplate()

        return mongodbTemplate.findById(ObjectId(id), collectionName)
            ?: throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar o pagamento com o id $id"
            )
    }

    override fun deleteById(id: String) {
        try {
            val mongodbTemplate = Connection.getTemplate()
            val mongodbCollection = mongodbTemplate.getCollection(collectionName)
            val deleted = mongodbCollection.deleteOne(Document("_id", ObjectId(id)))
            if (deleted.deletedCount <= 0) {
                throw HttpServerErrorException(
                    HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar o pagamento para remover."
                )
            }
            mongodbTemplate.getCollection("payment").deleteOne(Document("_id", ObjectId(id)))
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível remover o pagamento.\n$error"
            )
        }
    }

    override fun deleteByScheduleId(scheduleId: String): String {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria("scheduleId").isEqualTo(scheduleId))
        val deleted = mongodbTemplate.remove(query, collectionName)

        if (deleted.deletedCount < 1L) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível remover a receita por agendamento."
            )
        }

        return scheduleId
    }

    private fun toPaymentEntity(income: IncomeEntity): PaymentEntity {
        return PaymentEntity(
            income.id,
            income.date,
            PaymentTypeEntity(
                income.paymentType.id,
                income.paymentType.type,
                income.paymentType.description,
                income.paymentType.value
            ),
            income.description
        )
    }
}
