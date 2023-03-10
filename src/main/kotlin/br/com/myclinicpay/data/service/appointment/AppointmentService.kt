package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AppointmentService(
    private val findPersonByIdRepository: FindPersonByIdRepository,
    private val userRepository: UserRepository,
    private val appointmentRepository: AppointmentRepository
) : AppointmentService {
    private val timeZoneOffset = 3L
    override fun createOrUpdate(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val userEntity = userRepository.findById(appointment.userId)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Usuário não encontrado")

        val adaptedAppointment = toEntity(personEntity, userEntity, appointment)

        val appointmentExists = appointmentRepository.findByDateAndUserId(adaptedAppointment.date, appointment.userId)
            ?: return appointmentRepository.create(adaptedAppointment).id.toString()

        val sameAppointment = appointmentExists.scheduled.find { it.at == appointment.at }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Já existe um mesmo agendamento")
        }

        appointmentExists.scheduled.addAll(adaptedAppointment.scheduled)

        appointmentRepository.updateScheduledEntityById(appointmentExists.id, appointmentExists.scheduled)

        return appointmentExists.id.toString()
    }

    override fun findByDateAndUserId(date: LocalDate, userId: String): AppointmentEntity {
        val appointment = appointmentRepository.findByDateAndUserId(date, userId)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar uma agenda")

        appointment.scheduled.sortBy { it.at }

        return appointment
    }

    private fun toEntity(
        personEntity: PersonEntity,
        userEntity: UserEntity,
        appointment: Appointment
    ): AppointmentEntity {

        return AppointmentEntity(
            ObjectId.get(),
            userEntity,
            appointment.at.minusHours(timeZoneOffset).toLocalDate(),
            mutableListOf(
                ScheduledEntity(
                    appointment.at.minusHours(timeZoneOffset),
                    appointment.duration,
                    personEntity,
                    enumValueOf<AppointmentTypeEntity>(appointment.appointmentType.name).type,
                    appointment.description
                )
            )
        )
    }
}