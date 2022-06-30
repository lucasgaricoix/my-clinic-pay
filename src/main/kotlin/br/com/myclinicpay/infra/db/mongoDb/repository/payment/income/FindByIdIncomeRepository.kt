package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindByIdIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class FindByIdIncomeRepository : FindByIdIncomeRepository {
    override fun findById(id: String): IncomeEntity {
        val mongoTemplate = Connection.getTemplate()
        val incomeEntity = mongoTemplate.findById<IncomeEntity>(ObjectId(id), "income")
            ?: throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar o pagamento com o id $id"
            )

        return incomeEntity
    }
}
