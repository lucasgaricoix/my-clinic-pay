package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduledEntity
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

@Service
class AppointmentService(
    private val findPersonByIdRepository: FindPersonByIdRepository,
    private val appointmentRepository: AppointmentRepository
) : AppointmentService {
    override fun create(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val adaptedAppointment = toEntity(personEntity, appointment)

        val appointmentExists = appointmentRepository.findByDateAndUser(adaptedAppointment.date, appointment.user)
            ?: return appointmentRepository.create(adaptedAppointment).id.toString()

        val sameAppointment = appointmentExists.scheduled.find { it.at == appointment.at }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Already have same appointment")
        }

        appointmentExists.scheduled.addAll(adaptedAppointment.scheduled)

        appointmentRepository.updateScheduledEntityById(appointmentExists.id, appointmentExists.scheduled)

        return appointmentExists.id.toString()
    }

    private fun toEntity(personEntity: PersonEntity, appointment: Appointment): AppointmentEntity {
        return AppointmentEntity(
            ObjectId.get(),
            appointment.user,
            appointment.at.toLocalDate(),
            mutableListOf(
                ScheduledEntity(
                    appointment.at,
                    appointment.duration,
                    personEntity,
                    enumValueOf<AppointmentTypeEntity>(appointment.appointmentType.name).type,
                    appointment.description
                )
            )
        )
    }
}