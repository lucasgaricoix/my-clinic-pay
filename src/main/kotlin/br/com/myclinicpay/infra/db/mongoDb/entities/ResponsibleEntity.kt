package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("responsible")
class ResponsibleEntity(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val name: String
)
