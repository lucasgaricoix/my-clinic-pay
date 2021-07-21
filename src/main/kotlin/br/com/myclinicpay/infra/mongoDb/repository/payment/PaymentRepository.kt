package br.com.myclinicpay.infra.mongoDb.repository.payment

import br.com.myclinicpay.infra.mongoDb.entities.PaymentEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PaymentRepository : MongoRepository<PaymentEntity, ObjectId> {
}