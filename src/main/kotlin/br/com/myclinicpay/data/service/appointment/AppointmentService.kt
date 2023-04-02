package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.payment.income.CreateIncomeRepository
import br.com.myclinicpay.data.usecases.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class AppointmentService(
    private val findPersonByIdRepository: FindPersonByIdRepository,
    private val userRepository: UserRepository,
    private val appointmentRepository: AppointmentRepository,
    private val createIncomeRepository: CreateIncomeRepository,
    private val findAllBySessionIdIncomeRepository: FindAllBySessionIdIncomeRepository
) : AppointmentService {
    private val timeZoneOffset = 3L
    override fun createOrUpdate(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val userEntity = userRepository.findById(appointment.userId) ?: throw HttpServerErrorException(
            HttpStatus.NOT_FOUND, "Usuário não encontrado"
        )

        val adaptedAppointment = toEntity(personEntity, userEntity, appointment)

        val appointmentExists = appointmentRepository.findByDateAndUserId(adaptedAppointment.date, appointment.userId)

        if (appointmentExists == null) {
            val appointmentCreated = appointmentRepository.create(adaptedAppointment)
            this.createPayment(appointmentCreated, personEntity)
            return appointmentCreated.id.toString()
        }

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

        return appointments
    }

    private fun createPayment(appointment: AppointmentEntity, personEntity: PersonEntity) {
        val lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        val initialRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1)
        val finalRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, lastDayMonth)
        if (personEntity.paymentType == null) {
            throw Exception("O tipo de pagamento precisa ser cadastrado para o paciente.")
        }

        val income = Income(
            ObjectId.get().toString(),
            date = appointment.date,
            paymentType = personEntity.paymentType.toModel(),
            description = "receita criada a partir do agendamento",
            sessionNumber = null,
            isPaid = false,
            isPartial = false,
            isAbsence = false,
            person = personEntity.toModel()
        )

        this.createIncomeRepository.create(
            income,
            this.findAllBySessionIdIncomeRepository.findAll(initialRange, finalRange)
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
