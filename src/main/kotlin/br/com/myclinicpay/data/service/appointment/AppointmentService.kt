package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.data.usecases.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.data.usecases.payment.income.IncomeRepository
import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
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
    private val incomeRepository: IncomeRepository,
    private val findAllBySessionIdIncomeRepository: FindAllBySessionIdIncomeRepository
) : AppointmentService {
    private val timeZoneOffset = 3L
    override fun createOrUpdate(appointment: Appointment): String {
        val personEntity = findPersonByIdRepository.findEntityById(appointment.patientId)
        val userEntity = userRepository.findById(appointment.userId) ?: throw HttpServerErrorException(
            HttpStatus.NOT_FOUND, "Usuário não encontrado"
        )

        val adaptedAppointment = appointment.toEntity(personEntity, userEntity)

        val appointmentExists = appointmentRepository.findByDateAndUserId(adaptedAppointment.date, appointment.userId)

        if (appointmentExists == null) {
            val appointmentCreated = appointmentRepository.create(adaptedAppointment)
            this.createPayment(appointmentCreated, personEntity, null)
            return appointmentCreated.id.toString()
        }

        val sameAppointment = appointmentExists.schedules.find { it.start == appointment.at.minusHours(timeZoneOffset) }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Já existe um mesmo agendamento")
        }

        appointmentExists.schedules.addAll(adaptedAppointment.schedules)
        appointmentExists.unavailableSchedules.addAll(adaptedAppointment.unavailableSchedules)

        appointmentRepository.updateScheduledEntityById(appointmentExists.id, appointmentExists)
        this.createPayment(appointmentExists, personEntity, adaptedAppointment.schedules.first().id.toString())

        return appointmentExists.id.toString()
    }

    override fun findByDateAndUserId(date: LocalDateTime, userId: String): AppointmentDTO {
        val zonedTime = date.minusHours(timeZoneOffset).toLocalDate()
        val appointment = appointmentRepository.findByDateAndUserId(zonedTime, userId)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar uma agenda")

        appointment.schedules.sortBy { it.start }

        return appointment.toDTO()
    }

    override fun findWeeklyAppointments(from: LocalDate, to: LocalDate): List<AppointmentDTO> {
        val appointments = this.appointmentRepository.findAllByDateIntervals(from, to)

        if (appointments.isEmpty()) {
            throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar agenda com o período informado"
            )
        }

        appointments.sortedBy { it.date }

        for (appointment in appointments) {
            appointment.schedules.sortBy { it.start }
        }

        return appointments.map { it.toDTO() }
    }

    override fun deleteById(id: String, scheduleId: String): String {
        val deletedId = this.appointmentRepository.deleteByScheduleId(id, scheduleId)

        if (deletedId.isNotBlank()) {
            this.deletePayment(scheduleId)
        }

        return deletedId
    }

    private fun createPayment(
        appointment: AppointmentEntity,
        personEntity: PersonEntity,
        scheduleId: String?
    ): String? {
        if (personEntity.paymentType == null) {
            throw HttpServerErrorException(
                HttpStatus.FORBIDDEN,
                "O tipo de pagamento precisa ser cadastrado para o paciente."
            )
        }

        val getScheduleId = scheduleId ?: appointment.schedules.first().id.toString()

        val lastDayMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        val initialRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1)
        val finalRange = LocalDate.of(LocalDate.now().year, LocalDate.now().month, lastDayMonth)
        val lastSession = this.findAllBySessionIdIncomeRepository.findAll(initialRange, finalRange)

        val income = Income(
            ObjectId.get().toString(),
            date = appointment.date,
            paymentType = personEntity.paymentType.toModel(),
            description = "Receita criada a partir do agendamento",
            sessionNumber = null,
            isPaid = false,
            isPartial = false,
            isAbsence = false,
            person = personEntity.toModel(),
            scheduleId = getScheduleId,
        )

        val created = this.incomeRepository.create(income, lastSession)

        return created.id
    }

    private fun deletePayment(scheduleId: String): String {
        return this.incomeRepository.deleteByScheduleId(scheduleId)
    }
}
