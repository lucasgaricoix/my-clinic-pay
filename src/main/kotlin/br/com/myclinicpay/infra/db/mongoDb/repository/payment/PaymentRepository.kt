package br.com.myclinicpay.infra.db.mongoDb.repository.payment

import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PaymentRepository : MongoRepository<IncomeEntity, ObjectId> {
}
