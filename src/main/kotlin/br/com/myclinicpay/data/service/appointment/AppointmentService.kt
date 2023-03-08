package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduledEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class AppointmentService(
    private val findPersonByIdRepository: FindPersonByIdRepository,
    private val appointmentRepository: AppointmentRepository
) : AppointmentService {
    override fun create(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val adaptedAppointment = toEntity(personEntity, appointment)
        val appointmentData = appointmentRepository.create(adaptedAppointment, appointment.at.minusHours(3))
        return appointmentData.id.toString()
    }

    private fun toEntity(personEntity: PersonEntity, appointment: Appointment): AppointmentEntity {
        return AppointmentEntity(
            ObjectId.get(),
            appointment.user,
            appointment.at.minusHours(3).toLocalDate(),
            mutableListOf(
                ScheduledEntity(
                    appointment.at.minusHours(3),
                    appointment.duration,
                    personEntity,
                    appointment.type,
                    appointment.description
                )
            )
        )
    }
}