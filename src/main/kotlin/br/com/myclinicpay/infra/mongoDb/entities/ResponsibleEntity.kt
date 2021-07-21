package br.com.myclinicpay.infra.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("responsible")
class ResponsibleEntity(
    @Id
    val id: ObjectId? = null,
    val name: String
)