package br.com.myclinicpay.domain.model.user

import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduleRulesEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduleSettingsEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.SettingsEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.bson.types.ObjectId

class User(
    val id: String?,
    val name: String,
    val email: String,
    val password: String,
    val picture: String,
    val role: String = "standard",
    val settings: Settings,
    val tenantId: String? = "default"
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            ObjectId.get(),
            this.name,
            this.email,
            this.password,
            this.picture,
            this.role,
            SettingsEntity(
                ObjectId.get(),
                ScheduleSettingsEntity(
                    ObjectId.get(),
                    this.settings.schedule.rules.map { rule ->
                        ScheduleRulesEntity(
                            ObjectId.get(),
                            rule.name,
                            rule.intervals.map { Interval(it.from, it.to) }
                        )
                    }
                )
            )
        )
    }

    fun fromEntity(userEntity: UserEntity): User {
        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.email,
            userEntity.password,
            userEntity.picture,
            userEntity.role,
            Settings(
                Schedule(
                    userEntity.settings.schedule.rules.map { rule ->
                        ScheduleRules(
                            rule.name,
                            rule.intervals.map { Interval(it.from, it.to) })
                    }
                )
            ),
            userEntity.id.toString()
        )
    }
}
