package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.domain.model.user.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UserEntity(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val name: String,
    val email: String,
    val password: String,
    val picture: String,
    val role: String,
    val settings: SettingsEntity
) {
    fun toModel(): User {
        return User(
            this.id.toString(),
            this.name,
            this.email,
            this.password,
            this.picture,
            this.role,
            Settings(
                Schedule(
                    this.settings.schedule.rules.map { rule ->
                        ScheduleRules(
                            rule.name,
                            rule.intervals.map { Interval(it.from, it.to) })
                    }
                )
            ),
            this.id.toString()
        )
    }
}
