package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.data.usecases.payment.income.IncomeRepository
import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentType
import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import br.com.myclinicpay.infra.db.mongoDb.repository.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
class AppointmentServiceTest {
    @Autowired
    private lateinit var aesUtil: AESUtil

    private val findPersonByIdRepository = mock<FindPersonRepository>()
    private val userRepository = mock<UserRepository>()
    private val appointmentRepositoryMock = mock<AppointmentRepository>()
    private val incomeRepositoryMock = mock<IncomeRepository>()
    private val findAllBySessionIdIncomeRepositoryMock = mock<FindAllBySessionIdIncomeRepository>()

    private val service = AppointmentService(
        findPersonByIdRepository,
        userRepository,
        appointmentRepositoryMock,
        incomeRepositoryMock,
        findAllBySessionIdIncomeRepositoryMock
    )

    @Test
    fun findWeeklyAppointments() {
        val from = LocalDate.now()
        val to = from.plusDays(6)

        whenever(appointmentRepositoryMock.findAllByDateIntervals(from, to,))
            .thenReturn(this.getListOfAppointment(from.plusDays(3)))

        val appointments = service.findWeeklyAppointments(from, to)

        Mockito.verify(appointmentRepositoryMock).findAllByDateIntervals(from, to,)

        Assertions.assertEquals(2, appointments.size)
    }

    @Test
    fun createAppointment() {
        val appointmentEntity = this.getAppointmentEntity(LocalDate.now())
        whenever(findPersonByIdRepository.findEntityById("123"))
            .thenReturn(this.getPerson())
        whenever(this.userRepository.findById("123"))
            .thenReturn(this.getUser().toEntity(aesUtil))
        whenever(appointmentRepositoryMock.findByDateAndUserId(LocalDate.now(), "123"))
            .thenReturn(appointmentEntity)
        whenever(appointmentRepositoryMock.updateScheduledEntityById(ObjectId.get(), appointmentEntity))
            .thenReturn("123456")

        val created = service.createOrUpdate(this.getAppointmentModel())

        Assertions.assertEquals(appointmentEntity.id.toString(), created)
    }

    private fun getListOfAppointment(date: LocalDate): List<AppointmentEntity> {
        return mutableListOf(this.getAppointmentEntity(date), this.getAppointmentEntity(date.plusDays(1)))
    }

    private fun getPerson(): PersonEntity {
        return PersonEntity(
            ObjectId.get(),
            "Lucas",
            LocalDate.of(1990, 1, 1),
            ResponsibleEntity(
                ObjectId.get(),
                "Marushia"
            ),
            PaymentTypeEntity(
                ObjectId.get(),
                "income",
                "particular",
                100.0
            )
        )
    }

    private fun getAppointmentModel(): Appointment {
        return Appointment(
            patientId = "123",
            userId = "123",
            LocalDateTime.now(),
            60,
            AppointmentType.CONFIRMED,
            "Primeira sessão"
        )
    }

    private fun getAppointmentEntity(date: LocalDate): AppointmentEntity {
        return AppointmentEntity(
            ObjectId.get(),
            this.getUser().toEntity(aesUtil),
            date,
            mutableListOf(
                ScheduleEntity(
                    ObjectId.get(),
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(30),
                    30,
                    PersonEntity(
                        ObjectId.get(),
                        "Patient Test",
                        LocalDate.of(1990, 1, 1),
                        ResponsibleEntity(
                            ObjectId.get(),
                            "Responsible Test",
                        ),
                        PaymentTypeEntity(
                            ObjectId.get(),
                            "Receita",
                            "Particular A",
                            100.0,
                        )
                    ),
                    "schedule",
                    "Primeira sesão",
                )
            )
        )
    }

    private fun getUser(): User {
        val id = ObjectId.get().toString()
        return User(
            id,
            "Lucas",
            "lucasgaricoix@gmail.com",
            "123",
            "http://picture.test.com",
            "standard",
            Settings(
                Schedule(
                    listOf(ScheduleRules("sunday", listOf(Interval("08:00", "18:00"))))
                )
            ),
            id
        )
    }
}
