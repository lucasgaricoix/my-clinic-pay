package br.com.myclinicpay.infra.db.mongoDb.repository.user

import br.com.myclinicpay.data.service.authentication.AESUtil
import br.com.myclinicpay.data.usecases.user.UserRepository
import br.com.myclinicpay.domain.model.user.User
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpServerErrorException

@Repository
@Transactional
class UserRepository : UserRepository {
    private val collectionName = "user"

    override fun createUser(user: User, aesUtil: AESUtil): UserEntity {
        val mongodbTemplate = Connection.getTemplate()
        return mongodbTemplate.save<UserEntity>(user.toEntity(aesUtil), collectionName)
    }

    override fun updateUser(user: User, aesUtil: AESUtil): UserEntity {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("email").isEqualTo(user.email))
        val userEntity = user.toEntity(aesUtil)
        val toUpdate = Update()
            .set("picture", userEntity.picture)
            .set("role", userEntity.role)
            .set("settings", userEntity.settings)

        val updated = mongodbTemplate.updateFirst<UserEntity>(query, toUpdate, collectionName)

        if (updated.modifiedCount < 1) {
            throw HttpServerErrorException(HttpStatus.NOT_MODIFIED, "Não foi possível atualizar o cadastro")
        }

        return userEntity
    }

    override fun findByEmail(email: String): UserEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("email").`is`(email))
        return mongodbTemplate.findOne(query, collectionName)
    }

    override fun findById(id: String): UserEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("_id").isEqualTo(id))
        return mongodbTemplate.findOne(query, collectionName)
    }

    override fun findByName(name: String): UserEntity? {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("name").isEqualTo(name))
        return mongodbTemplate.findOne(query, collectionName)
    }
}
