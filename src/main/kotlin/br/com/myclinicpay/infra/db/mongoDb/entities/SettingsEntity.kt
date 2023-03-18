package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class SettingsEntity(
    @Id
    val id: ObjectId,
    val schedule: ScheduleSettingsEntity
)
