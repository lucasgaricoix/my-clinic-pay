package br.com.myclinicpay.infra.db.mongoDb.repository.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import com.mongodb.BasicDBObject
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDate

@Repository
class AppointmentRepository : AppointmentRepository {
    private val collectionName = "appointment"
    private val schedules = "schedules"
    override fun create(appointmentEntity: AppointmentEntity): AppointmentEntity {
        val mongodbTemplate = Connection.getTemplate()
        return mongodbTemplate.save(appointmentEntity, collectionName)
    }

    override fun findByDateAndUserId(date: LocalDate, userId: String): AppointmentEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(
            Criteria.where("date").isEqualTo(date).and("userId").isEqualTo(userId)
        )

        return mongodbTemplate.findOne(query, collectionName)
    }

    override fun updateScheduledEntityById(
        id: ObjectId?, appointmentEntity: AppointmentEntity
    ): String {
        val mongodbTemplate = Connection.getTemplate()
        val updateQuery = Query(Criteria.where("_id").isEqualTo(id))
        val toUpdate = Update().set(schedules, appointmentEntity.schedules)
            .set("unavailableSchedules", appointmentEntity.unavailableSchedules)
        val updated = mongodbTemplate.updateFirst<AppointmentEntity>(updateQuery, toUpdate, collectionName)

        if (updated.modifiedCount <= 0) {
            throw HttpServerErrorException(HttpStatus.NOT_MODIFIED, "Não foi possível modificar o agendamento")
        }

        return id.toString()
    }

    override fun findAllByDateIntervals(from: LocalDate, to: LocalDate, userId: String): List<AppointmentEntity> {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("date").gte(from).lte(to).and("userId").isEqualTo(userId))
        return mongodbTemplate.find(query, collectionName)
    }

    override fun deleteByScheduleId(id: String, scheduleId: String): String {
        val mongodbTemplate = Connection.getTemplate()
        val pull = Update().pull(schedules, BasicDBObject("_id", scheduleId))
        val updated = mongodbTemplate.updateMulti(Query(Criteria("_id").isEqualTo(id)), pull, collectionName)

        if (updated.modifiedCount < 1L) {
            throw HttpServerErrorException(HttpStatus.NOT_MODIFIED, "Não foi possível deletear o agendamento.")
        }
        return scheduleId
    }
}
