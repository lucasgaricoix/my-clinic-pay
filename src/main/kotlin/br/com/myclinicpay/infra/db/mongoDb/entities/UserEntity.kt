package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UserEntity(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val name: String,
    val email: String,
    val picture: String,
    val role: String
)
