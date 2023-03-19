package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class ScheduleSettingsEntity(
    @Id
    val id: ObjectId,
    val rules: List<ScheduleRulesEntity> = listOf()
)
