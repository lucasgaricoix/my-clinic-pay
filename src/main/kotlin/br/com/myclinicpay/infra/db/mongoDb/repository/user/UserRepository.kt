package br.com.myclinicpay.infra.db.mongoDb.repository.user

import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository : UserRepository {
    private val collectionName = "user"

    override fun createUser(user: User): UserEntity {
        val mongodbTemplate = Connection.getTemplate()
        val userExists = findByEmail(user.email)

        if (userExists.isEmpty()) {
            val userEntity = UserEntity(
                ObjectId.get(),
                user.name,
                user.email,
                user.picture,
                user.role
            )
            return mongodbTemplate.save<UserEntity>(userEntity, collectionName)
        }

        return userExists.first()
    }

    override fun findByEmail(email: String): List<UserEntity> {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("email").`is`(email))
        return mongodbTemplate.find(query, collectionName)
    }
}
