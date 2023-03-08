package br.com.myclinicpay.infra.db.mongoDb.repository.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduledEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDateTime

@Repository
class AppointmentRepository : AppointmentRepository {
    private val collectionName = "appointment"
    override fun create(appointmentEntity: AppointmentEntity, at: LocalDateTime): AppointmentEntity {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(
            Criteria.where("date")
                .isEqualTo(appointmentEntity.date)
                .and("user")
                .isEqualTo(appointmentEntity.user)
        )
        val appointmentExists = mongodbTemplate.find<AppointmentEntity>(query)

        if (appointmentExists.isEmpty()) {
            return mongodbTemplate.save(appointmentEntity, collectionName)
        }

        val appointment = appointmentExists.first()

        val sameAppointment = appointment.scheduled.find { it.at == at }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Already have same appointment")
        }

        appointment.scheduled.addAll(appointmentEntity.scheduled)

        val updatedId = updateScheduledEntityById(appointmentExists.first().id, appointment.scheduled)

        return mongodbTemplate.findById(updatedId, collectionName)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Appointment not found")
    }

    private fun updateScheduledEntityById(id: ObjectId?, scheduled: MutableList<ScheduledEntity>): String {
        val mongodbTemplate = Connection.getTemplate()
        val updateQuery = Query(Criteria.where("_id").isEqualTo(id))
        val toUpdate = Update().set("scheduled", scheduled)
        val updated = mongodbTemplate.updateFirst<AppointmentEntity>(updateQuery, toUpdate, collectionName)

        if (updated.modifiedCount <= 0) {
            throw HttpServerErrorException(HttpStatus.NOT_MODIFIED, "Appointment not updated")
        }

        return id.toString()
    }
}