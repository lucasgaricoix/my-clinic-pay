package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.data.service.payment.income.CreateIncomeService
import br.com.myclinicpay.data.service.payment.income.DeleteIncomeService
import br.com.myclinicpay.data.service.payment.type.FindPaymentTypeByIdService
import br.com.myclinicpay.data.service.person.FindPersonService
import br.com.myclinicpay.data.service.user.UserService
import br.com.myclinicpay.data.usecases.appointment.AppointmentRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AppointmentService @Autowired constructor(
    val findPersonService: FindPersonService,
    val userService: UserService,
    val deleteIncomeService: DeleteIncomeService,
    val createIncomeService: CreateIncomeService,
    val findPaymentTypeByIdService: FindPaymentTypeByIdService,
    val appointmentRepository: AppointmentRepository,
    val aesUtil: AESUtil
) : AppointmentService {

    private val timeZoneOffset = 3L
    override fun createOrUpdate(appointment: Appointment): String {
        val person = findPersonService.findById(appointment.patientId)
        val paymentType =
            person.paymentType?.let { paymentType -> paymentType.id?.let { findPaymentTypeByIdService.findById(it) } }

        val adaptedAppointment = appointment.toEntity()

        val appointmentExists = appointmentRepository.findByDateAndUserId(adaptedAppointment.date, appointment.userId)

        if (appointmentExists == null) {
            val appointmentCreated = appointmentRepository.create(adaptedAppointment)
            createPayment(appointmentCreated, person.toEntity(), paymentType?.toEntity(), null)
            return appointmentCreated.id.toString()
        }

        val sameAppointment = appointmentExists.schedules.find { it.start == appointment.at.minusHours(timeZoneOffset) }

        if (sameAppointment != null) {
            throw HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE, "Já existe um mesmo agendamento")
        }

        appointmentExists.schedules.addAll(adaptedAppointment.schedules)

        appointmentRepository.updateScheduledEntityById(appointmentExists.id, appointmentExists)

        this.createPayment(
            appointmentExists,
            person.toEntity(),
            paymentType?.toEntity(),
            adaptedAppointment.schedules.first().id.toString()
        )

        return appointmentExists.id.toString()
    }

    override fun findByDateAndUserId(date: LocalDateTime, userId: String): AppointmentDTO {
        val user = userService.findUserById(userId)
        val zonedTime = date.minusHours(timeZoneOffset).toLocalDate()
        val appointment = appointmentRepository.findByDateAndUserId(zonedTime, userId)
            ?: throw HttpServerErrorException(HttpStatus.NOT_FOUND, "Não foi possível encontrar uma agenda")

        appointment.schedules.sortBy { it.start }

        val scheduleDTO = appointment.schedules.map {
            val patient = findPersonService.findById(it.patientId.toString())
            it.toDTO(patient)
        }

        return appointment.toDTO(user.toEntity(aesUtil), scheduleDTO)
    }

    override fun findWeeklyAppointments(from: LocalDate, to: LocalDate, userId: String): List<AppointmentDTO> {
        val appointments = appointmentRepository.findAllByDateIntervals(from, to, userId)

        if (appointments.isEmpty()) {
            throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar agenda com o período informado"
            )
        }

        val user = userService.findUserById(appointments.first().userId)

        appointments.sortedBy { it.date }

        for (appointment in appointments) {
            appointment.schedules.sortBy { it.start }
        }

        return appointments.map {
            val scheduleDTO = it.schedules.map { schedule ->
                val patient = this.findPersonService.findById(schedule.patientId.toString())
                schedule.toDTO(patient)
            }
            it.toDTO(user.toEntity(aesUtil), scheduleDTO)
        }
    }

    override fun deleteById(id: String, scheduleId: String): String {
        val deletedId = this.appointmentRepository.deleteByScheduleId(id, scheduleId)

        if (deletedId.isNotBlank()) {
            deleteIncomeService.deleteByScheduleId(scheduleId)
        }

        return deletedId
    }

    private fun createPayment(
        appointment: AppointmentEntity,
        personEntity: PersonEntity,
        paymentType: PaymentTypeEntity?,
        scheduleId: String?
    ): String? {
        if (paymentType == null) {
            throw HttpServerErrorException(
                HttpStatus.FORBIDDEN,
                "O tipo de pagamento precisa ser cadastrado para o paciente."
            )
        }

        val getScheduleId = scheduleId ?: appointment.schedules.first().id.toString()

        val income = Income(
            ObjectId.get().toString(),
            date = appointment.date,
            paymentType = paymentType.toModel(),
            description = "Receita criada a partir do agendamento",
            sessionNumber = null,
            isPaid = false,
            isPartial = false,
            isAbsence = false,
            person = personEntity.toModel(paymentType),
            scheduleId = getScheduleId,
        )

        val created = createIncomeService.create(income)

        return created.id
    }
}
