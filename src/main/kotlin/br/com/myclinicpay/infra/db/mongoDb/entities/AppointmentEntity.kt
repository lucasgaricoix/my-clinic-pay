package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("appointment")
class AppointmentEntity(
    @Id
    val id: ObjectId?,
    val user: String,
    val date: LocalDate = LocalDate.now(),
    val scheduled: MutableList<ScheduledEntity>,
)