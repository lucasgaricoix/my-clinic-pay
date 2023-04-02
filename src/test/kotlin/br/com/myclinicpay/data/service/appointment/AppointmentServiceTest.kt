package br.com.myclinicpay.data.service.appointment

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import br.com.myclinicpay.infra.db.mongoDb.repository.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
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

    private val findPersonByIdRepository = mock<FindPersonByIdRepository>()
    private val userRepository = mock<UserRepository>()
    private val appointmentRepositoryMock = mock<AppointmentRepository>()

    @Test
    fun findWeeklyAppointments() {
        val from = LocalDate.now()
        val to = from.plusDays(6)

        whenever(appointmentRepositoryMock.findAllByDateIntervals(from, to))
            .thenReturn(this.getListOfAppointment(from.plusDays(3)))

        val service = AppointmentService(findPersonByIdRepository, userRepository, appointmentRepositoryMock)

        val appointments = service.findWeeklyAppointments(from, to)

        Mockito.verify(appointmentRepositoryMock).findAllByDateIntervals(from, to)

        Assertions.assertEquals(2, appointments.size)
    }

    private fun getListOfAppointment(date: LocalDate): List<AppointmentEntity> {
        return mutableListOf(this.getAppointment(date), this.getAppointment(date.plusDays(1)))
    }

    private fun getAppointment(date: LocalDate): AppointmentEntity {
        return AppointmentEntity(
            ObjectId.get(),
            this.getUser().toEntity(aesUtil),
            date,
            mutableListOf(
                ScheduleEntity(
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
                    "Schedule",
                    null
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
