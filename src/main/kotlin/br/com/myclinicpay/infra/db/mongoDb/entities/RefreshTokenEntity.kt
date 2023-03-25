package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("refresh_token")
class RefreshTokenEntity(
    @Id
    val id: ObjectId,
    val user: UserEntity,
    val token: String,
    val expirationDate: LocalDateTime
)
