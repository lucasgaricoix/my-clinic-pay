package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate
import java.time.LocalDateTime

@Document("person")
data class PersonEntity(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val name: String,
    val birthDate: LocalDate,
    val age: Int,
    val responsible: ResponsibleEntity
)
