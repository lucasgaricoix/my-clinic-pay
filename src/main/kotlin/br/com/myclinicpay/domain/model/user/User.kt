package br.com.myclinicpay.domain.model.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduleRulesEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduleSettingsEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.SettingsEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

data class User(
    val id: String?,
    val name: String,
    val email: String,
    val password: String,
    val picture: String,
    val role: String = "standard",
    val settings: Settings?,
    val tenantId: String? = "default"
) : Serializable {

    constructor(
        id: String?,
        name: String,
        email: String,
        password: String,
        picture: String,
        role: String,
    ) : this(id, name, email, password, picture, role, null, null)

    fun toEntity(aesUtil: AESUtil): UserEntity {
        if (this.settings != null) {
            return UserEntity(
                id = ObjectId.get(),
                name = this.name,
                email = this.email,
                password = aesUtil.encode(this.password),
                picture = this.picture,
                role = this.role,
                settings = SettingsEntity(
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

        return UserEntity(
            ObjectId.get(),
            this.name,
            this.email,
            this.password,
            this.picture,
            this.role,
        )
    }

    fun fromEntity(userEntity: UserEntity): User {
        if (userEntity.settings != null) {
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

        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.email,
            userEntity.password,
            userEntity.picture,
            userEntity.role,
        )
    }
}
