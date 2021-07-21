package br.com.myclinicpay.infra.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document("person")
data class PersonEntity(
    @Id
    val id: ObjectId? = null,
    val name: String,
    val birthDate: LocalDateTime,
    val age: Int,
    @Field("responsible")
    val responsible: ResponsibleEntity
)