package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.schedule.Interval
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class ScheduleRulesEntity(
    @Id
    val id: ObjectId,
    val name: String,
    val intervals: List<Interval>
)

