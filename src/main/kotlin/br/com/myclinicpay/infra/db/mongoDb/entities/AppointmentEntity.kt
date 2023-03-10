package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("appointment")
class AppointmentEntity(
    @Id
    val id: ObjectId?,
    val user: UserEntity,
    val date: LocalDate,
    val schedule: MutableList<ScheduleEntity>,
    val unavailableSchedule: MutableList<UnavailableScheduleEntity>
)