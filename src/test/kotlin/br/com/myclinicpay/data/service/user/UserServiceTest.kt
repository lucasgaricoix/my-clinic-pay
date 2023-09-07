package br.com.myclinicpay.data.service.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.domain.model.schedule.Interval
import br.com.myclinicpay.domain.model.schedule.Schedule
import br.com.myclinicpay.domain.model.schedule.ScheduleRules
import br.com.myclinicpay.domain.model.setting.Settings
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserServiceTest {
    private val userRepositoryMock = mock<UserRepository>()
    @Test
    fun `update should change results successfully`() {
        val user = this.getUser()
        val aesUtil = AESUtil()

        whenever(userRepositoryMock.updateUser(user, aesUtil))
            .thenReturn(user.toEntity(aesUtil))

        val service = UserService(userRepositoryMock)

        val userEntity = service.updateUser(user, aesUtil)

        Mockito.verify(userRepositoryMock).updateUser(user, aesUtil)

        Assertions.assertEquals("lucasgaricoix@gmail.com", userEntity.email)
    }

    @Test
    fun `findUserById should return successfully`() {
        val id = ObjectId.get().toString()
        val user = this.getUser()
        val aesUtil = AESUtil()

        whenever(userRepositoryMock.findById(id))
            .thenReturn(user.toEntity(aesUtil))

        val service = UserService(userRepositoryMock)

        val userEntity = service.findUserById(id)

        Mockito.verify(userRepositoryMock).findById(id)

        Assertions.assertEquals("lucasgaricoix@gmail.com", userEntity.email)
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
