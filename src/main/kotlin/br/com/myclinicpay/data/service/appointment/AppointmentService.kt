package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import org.apache.tomcat.jni.Local
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
    private val totalDaysOfWeek = 7
    private val timeZoneOffset = 3L
    override fun createOrUpdate(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val userEntity = userRepository.findById(appointment.userId) ?: throw HttpServerErrorException(
            HttpStatus.NOT_FOUND, "Usuário não encontrado"
        )

        val adaptedAppointment = toEntity(personEntity, userEntity, appointment)

        val appointmentExists = appointmentRepository.findByDateAndUserId(adaptedAppointment.date, appointment.userId)
            ?: return appointmentRepository.create(adaptedAppointment).id.toString()

        val sameAppointment = appointmentExists.schedule.find { it.start == appointment.at }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Já existe um mesmo agendamento")
        }

        appointmentExists.schedule.addAll(adaptedAppointment.schedule)
        appointmentExists.unavailableSchedule.addAll(adaptedAppointment.unavailableSchedule)

        appointmentRepository.updateScheduledEntityById(appointmentExists.id, appointmentExists)

        return appointmentExists.id.toString()
    }

    override fun findByDateAndUserId(date: LocalDateTime, userId: String): AppointmentEntity {
        val zonedTime = date.minusHours(timeZoneOffset).toLocalDate()
        val appointment = appointmentRepository.findByDateAndUserId(zonedTime, userId)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar uma agenda")

        appointment.schedule.sortBy { it.start }

        return appointment
    }

    override fun findWeeklyAppointments(from: LocalDate, to: LocalDate): List<AppointmentEntity> {
        val appointments = this.appointmentRepository.findAllByDateIntervals(from, to)

        if (appointments.isEmpty()) {
            throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar agenda com o período informado"
            )
        }

        appointments.sortedBy { it.date }

        if (appointments.size < totalDaysOfWeek) {
            return this.fillAppointments(appointments)
        }

        return appointments
    }

    private fun fillAppointments(appointments: List<AppointmentEntity>): List<AppointmentEntity> {
        val appointmentsList = mutableListOf<AppointmentEntity>()
        appointmentsList.addAll(appointments)

        val difference = totalDaysOfWeek - appointmentsList.size
        if (difference > 0) {
            val lastEntity = appointmentsList.lastOrNull()
            if (lastEntity != null) {
                for (index in 1..difference) {
                    appointmentsList.add(this.getFilledAppointment(lastEntity, index.toLong()))
                }
            }
            return appointmentsList
        }

        return listOf()
    }

    private fun getFilledAppointment(currentAppointment: AppointmentEntity, days: Long): AppointmentEntity {
        return AppointmentEntity(
            ObjectId.get(),
            currentAppointment.user,
            currentAppointment.date.plusDays(days),

        )
    }

    private fun toEntity(
        personEntity: PersonEntity, userEntity: UserEntity, appointment: Appointment
    ): AppointmentEntity {
        val zonedTime = appointment.at.minusHours(timeZoneOffset)
        return AppointmentEntity(
            ObjectId.get(), userEntity, zonedTime.toLocalDate(), mutableListOf(
                ScheduleEntity(
                    zonedTime,
                    zonedTime.plusMinutes(appointment.duration.toLong()),
                    appointment.duration,
                    personEntity,
                    enumValueOf<AppointmentTypeEntity>(appointment.appointmentType.name).type,
                    appointment.description
                )
            ), mutableListOf(
                UnavailableScheduleEntity(
                    zonedTime, zonedTime.plusMinutes(appointment.duration.toLong())
                )
            )
        )
    }
}
